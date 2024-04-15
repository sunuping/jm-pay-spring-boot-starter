package org.jm.pay.impl.wx;

import com.wechat.pay.java.service.payments.h5.H5Service;
import com.wechat.pay.java.service.payments.h5.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.bean.query.JmOrderQueryParam;
import org.jm.pay.bean.query.JmOrderQueryVO;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.constant.JmPayStatusConstant;
import org.jm.pay.i.JmWxPay;
import org.jm.pay.tools.BeanTools;

import java.math.BigDecimal;


/**
 * @author kong
 */
public class JmWxPayH5 implements JmWxPay {
    private final JmWxConfig config;
    private H5Service service;

    public JmWxPayH5(JmWxConfig config) {
        this.config = config;
    }

    @Override
    public void initConfig() {
        this.service = new H5Service.Builder().config(this.config.getConfig()).build();
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
        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setPayerClientIp(param.getPayerClientIp());
        H5Info h5Info = new H5Info();
        //场景类型
        //示例值：iOS, Android, Wap
        h5Info.setType("Wap");
        sceneInfo.setH5Info(h5Info);
        request.setSceneInfo(sceneInfo);

        return new JmPayVO().setResponse(this.getService().prepay(request).getH5Url());
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

    public H5Service getService() {
        if (this.service == null) {
            this.initConfig();
        }
        return this.service;
    }
}
