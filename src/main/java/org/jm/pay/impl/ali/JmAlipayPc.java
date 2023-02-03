package org.jm.pay.impl.ali;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.bean.query.JmOrderQueryParam;
import org.jm.pay.bean.query.JmOrderQueryVO;
import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.constant.JmPayStatusConstant;
import org.jm.pay.i.JmAlipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author kong
 */
@Slf4j
@Service
public class JmAlipayPc implements JmAlipay {
    private AlipayClient client;
    private final JmAlipayConfig config;

    @Autowired
    public JmAlipayPc(JmAlipayConfig config) {
        this.config = config;
    }

    @Override
    public void initConfig() {
        //获得初始化的AlipayClient
        client = new DefaultAlipayClient(this.config.getGatewayUrl(), this.config.getAppid(),
                this.config.getRsaPrivateKey(), this.config.getFormat(),
                this.config.getCharset(), this.config.getAlipayPublicKey(), this.config.getSignType());
    }

    @Override
    public JmPayVO pay(JmPayParam param) {
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 设置异步通知地址
        alipayRequest.setNotifyUrl(Optional.ofNullable(param.getNotifyUrl()).orElse(this.config.getNotifyUrl()));
        // 设置同步地址
        alipayRequest.setReturnUrl(Optional.ofNullable(param.getReturnUrl()).orElse(this.config.getReturnUrl()));

        JSONObject bizContentJson = new JSONObject();
        bizContentJson.put("out_trade_no", param.getOrderNo());
        bizContentJson.put("total_amount", param.getAmount().toString());
        bizContentJson.put("subject", param.getOrderName());
        bizContentJson.put("body", param.getDesc());
        bizContentJson.put("product_code", "FAST_INSTANT_TRADE_PAY");
        //请求超时
        bizContentJson.put("timeout_express", "10m");
        alipayRequest.setBizContent(bizContentJson.toJSONString());
        try {
            String res = this.getClient().pageExecute(alipayRequest).getBody();
            return new JmPayVO().setResponse(res);
        } catch (AlipayApiException e) {

            return new JmPayVO().setErr(JSON.toJSONString(e));
        }

    }

    @Override
    public JmOrderQueryVO query(JmOrderQueryParam param) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject json = new JSONObject();
        json.put("out_trade_no", param.getOrderNo());
        request.setBizContent(json.toJSONString());
        try {
            JSONObject res = JSON.parseObject(this.getClient().execute(request).getBody());
            JSONObject payRes = res.getJSONObject("alipay_trade_query_response");
            JmOrderQueryVO vo = new JmOrderQueryVO().setAlipayOrderQueryVO(res);
            switch (payRes.getString("trade_status")) {
                //交易创建，等待买家付款
                case "WAIT_BUYER_PAY":
                    return vo.setOrderStatus(JmPayStatusConstant.NOT_PAY);
                //未付款交易超时关闭，或支付完成后全额退款
                case "TRADE_CLOSED":
                //交易结束，不可退款
                case "TRADE_FINISHED":
                    return vo.setOrderStatus(JmPayStatusConstant.FAIL);
                //交易支付成功
                case "TRADE_SUCCESS":
                    return new JmOrderQueryVO().setOrderStatus(JmPayStatusConstant.SUCCESS).setOutTradeNo(payRes.getString("out_trade_no"));
                default:
            }
            return new JmOrderQueryVO();
        } catch (AlipayApiException e) {
            return new JmOrderQueryVO();
        }

    }

    public AlipayClient getClient() {
        if (client == null) {
            this.initConfig();
        }
        return this.client;
    }
}
