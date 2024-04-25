package org.jm.pay.impl.ali;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import lombok.extern.slf4j.Slf4j;
import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.bean.query.JmOrderQueryParam;
import org.jm.pay.bean.query.JmOrderQueryVO;
import org.jm.pay.bean.transfer.JmTransferParam;
import org.jm.pay.bean.transfer.JmTransferVO;
import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.constant.JmPayStatusConstant;
import org.jm.pay.i.JmAlipay;

/**
 * @author kong
 */
@Slf4j
public class JmBaseAlipay implements JmAlipay {
    private final JmAlipayConfig config;

    public JmBaseAlipay(JmAlipayConfig config) {
        this.config = config;
    }

    @Override
    public JmPayVO pay(JmPayParam param) {
        return null;
    }

    @Override
    public JmOrderQueryVO query(JmOrderQueryParam param) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject json = new JSONObject();
        json.put("out_trade_no", param.getOrderNo());
        request.setBizContent(json.toJSONString());
        try {
            JSONObject res = JSON.parseObject(this.config.getClient().execute(request).getBody());
            JSONObject payRes = res.getJSONObject("alipay_trade_query_response");
            JmOrderQueryVO vo = new JmOrderQueryVO().setAlipayOrderQueryVO(res);
            return switch (payRes.getString("trade_status")) {
                //交易创建，等待买家付款
                case "WAIT_BUYER_PAY" -> vo.setOrderStatus(JmPayStatusConstant.NOT_PAY);
                //未付款交易超时关闭，或支付完成后全额退款
                //交易结束，不可退款
                case "TRADE_CLOSED", "TRADE_FINISHED" -> vo.setOrderStatus(JmPayStatusConstant.FAIL);
                //交易支付成功
                case "TRADE_SUCCESS" -> new JmOrderQueryVO()
                        .setOrderStatus(JmPayStatusConstant.SUCCESS)
                        .setOutTradeNo(payRes.getString("out_trade_no"))
                        .setPayTime(payRes.getString("send_pay_date"));
                default -> new JmOrderQueryVO();
            };
        } catch (AlipayApiException e) {
            log.error("", e);
            return new JmOrderQueryVO().setOrderStatus(JmPayStatusConstant.ALREADY_EXPIRE);
        }
    }

    @Override
    public Boolean close(String oid) {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("trade_no", oid);
        request.setBizContent(bizContent.toString());
        AlipayTradeCloseResponse response = null;
        try {
            response = this.config.getClient().execute(request);
            return response.isSuccess();
        } catch (AlipayApiException e) {
            log.error("", e);
            return false;
        }

    }

    @Override
    public void initConfig() {

    }

    @Override
    public JmTransferVO transfer(JmTransferParam param) {
        return null;
    }
}
