package org.jm.pay.impl.ali;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.extern.slf4j.Slf4j;
import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.i.JmAlipay;

import java.util.Optional;

/**
 * @author kong
 */
@Slf4j
public class JmAlipayPc extends JmBaseAlipay implements JmAlipay {
    private final JmAlipayConfig config;

    public JmAlipayPc(JmAlipayConfig config) {
        super(config);
        this.config = config;
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
            String res = this.config.getClient().pageExecute(alipayRequest).getBody();
            return new JmPayVO().setResponse(res);
        } catch (AlipayApiException e) {

            return new JmPayVO().setErr(JSON.toJSONString(e));
        }

    }

}
