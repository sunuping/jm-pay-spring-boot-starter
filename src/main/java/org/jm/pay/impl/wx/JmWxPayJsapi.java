package org.jm.pay.impl.wx;

import com.alibaba.fastjson2.JSON;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.bean.query.JmOrderQueryParam;
import org.jm.pay.bean.query.JmOrderQueryVO;
import org.jm.pay.bean.transfer.JmTransferParam;
import org.jm.pay.bean.transfer.JmTransferVO;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.constant.JmPayStatusConstant;
import org.jm.pay.i.JmWxPay;
import org.jm.pay.tools.BeanTools;

import java.math.BigDecimal;


/**
 * @author kong
 */
public class JmWxPayJsapi implements JmWxPay {
    private final JmWxConfig config;
    private JsapiServiceExtension service;

    public JmWxPayJsapi(JmWxConfig config) {
        this.config = config;
    }

    @Override
    public void initConfig() {
        this.service = new JsapiServiceExtension.Builder().config(this.config.getConfig()).signType("RSA").build();
    }

    @Override
    public JmPayVO pay(JmPayParam param) {
        PrepayRequest request = new PrepayRequest();
        request.setAppid(this.config.getAppid());
        request.setMchid(this.config.getMchId());
        request.setDescription(param.getDesc());
        request.setOutTradeNo(param.getOrderNo());
        request.setNotifyUrl(this.config.getNotifyUrl());
        Amount amount = new Amount();
        amount.setTotal(param.getAmount().multiply(new BigDecimal("100")).intValue());
        amount.setCurrency("CNY");
        request.setAmount(amount);
        //支付者信息
        Payer payer = new Payer();
        payer.setOpenid(param.getOpenid());
        request.setPayer(payer);

        PrepayWithRequestPaymentResponse prepayWithRequestPaymentResponse = this.getService().prepayWithRequestPayment(request);
        return new JmPayVO().setResponse(JSON.toJSONString(prepayWithRequestPaymentResponse));
    }

    @Override
    public Boolean close(String oid) {
        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(this.config.getMchId());
        request.setOutTradeNo(oid);
        this.getService().closeOrder(request);
        return true;
    }

    @Override
    public JmOrderQueryVO query(JmOrderQueryParam param) {
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(this.config.getMchId());
        request.setOutTradeNo(param.getOrderNo());
        Transaction transaction = this.getService().queryOrderByOutTradeNo(request);
        JmOrderQueryVO vo = BeanTools.map(transaction, JmOrderQueryVO.class);

        return switch (vo.getTradeState().toString()) {
            //支付成功
            case "SUCCESS" -> vo.setOrderStatus(JmPayStatusConstant.SUCCESS);
            //未支付
            //用户支付中
            case "NOTPAY", "USERPAYING" -> vo.setOrderStatus(JmPayStatusConstant.NOT_PAY);
            //转入退款
            //已关闭
            //已撤销(刷卡支付)
            //支付失败(其他原因，如银行返回失败)
            //已接收，等待扣款
            case "REFUND", "CLOSED", "REVOKED", "PAYERROR", "ACCEPT" -> vo.setOrderStatus(JmPayStatusConstant.FAIL).setPayTime(vo.getSuccessTime());
            default -> vo;
        };
    }

    public JsapiServiceExtension getService() {
        if (this.service == null) {
            this.initConfig();
        }
        return this.service;
    }


    @Override
    public JmTransferVO transfer(JmTransferParam param) {
        return null;
    }
}
