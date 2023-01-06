package org.jm.pay.impl.wx;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
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
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.i.JmWxPay;
import org.jm.pay.tools.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author kong
 */
@Slf4j
@Service
public class JmWxPayNative implements JmWxPay {
    private final JmWxConfig config;

    @Autowired
    public JmWxPayNative(JmWxConfig config) {
        this.config = config;
    }

    private NativePayService service;

    @Override
    public void initConfig() {
        // 初始化商户配置
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(this.config.getMchId())
//                .privateKeyFromPath(this.config.getPrivateKeyPath())
                .merchantSerialNumber(this.config.getMchSerialNumber())
                .privateKey(this.config.getPrivateKey())
                .apiV3Key(this.config.getApiV3Key())
                .build();
        this.service = new NativePayService.Builder().config(config).build();
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
    public JmOrderQueryVO query(JmOrderQueryParam param) {
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(this.config.getMchId());
        request.setOutTradeNo(param.getOrderNo());
        Transaction transaction = this.getService().queryOrderByOutTradeNo(request);
        return BeanTools.map(transaction, JmOrderQueryVO.class);
    }

    public NativePayService getService() {
        if (this.service == null) {
            this.initConfig();
        }
        return this.service;
    }
}
