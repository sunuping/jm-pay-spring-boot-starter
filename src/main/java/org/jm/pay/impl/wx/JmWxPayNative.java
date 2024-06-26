package org.jm.pay.impl.wx;

import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.QueryOrderByOutTradeNoRequest;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JmWxPayNative implements JmWxPay {
    private final JmWxConfig config;

    public JmWxPayNative(JmWxConfig config) {
        this.config = config;
    }

    private NativePayService service;

    @Override
    public void initConfig() {
        this.service = new NativePayService.Builder().config(this.config.getConfig()).build();
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
        request.setAmount(amount);
        return new JmPayVO().setResponse(this.getService().prepay(request).getCodeUrl());
    }

    @Override
    public Boolean close(String oid) {
        return null;
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

    public NativePayService getService() {
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
