package org.jm.pay.impl.wx;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author kong
 */
@Service
public class JmWxPayH5 implements JmWxPay {
    private final JmWxConfig config;
    private H5Service service;

    @Autowired
    public JmWxPayH5(JmWxConfig config) {
        this.config = config;
    }

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
        this.service = new H5Service.Builder().config(config).build();
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
        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setPayerClientIp("118.116.104.50");
        H5Info h5Info = new H5Info();
        //场景类型
        //示例值：iOS, Android, Wap
        h5Info.setType("Android");
        sceneInfo.setH5Info(h5Info);
        request.setSceneInfo(sceneInfo);

        return new JmPayVO().setResponse(this.getService().prepay(request).getH5Url());
    }


    @Override
    public JmOrderQueryVO query(JmOrderQueryParam param) {
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(this.config.getMchId());
        request.setOutTradeNo(param.getOrderNo());
        Transaction transaction = this.getService().queryOrderByOutTradeNo(request);
        JmOrderQueryVO vo = BeanTools.map(transaction, JmOrderQueryVO.class);

        switch (vo.getTradeState().toString()) {
            //支付成功
            case "SUCCESS":
                return vo.setOrderStatus(JmPayStatusConstant.SUCCESS);
            //未支付
            case "NOTPAY":
                //用户支付中
            case "USERPAYING":
                return vo.setOrderStatus(JmPayStatusConstant.NOT_PAY);
            //转入退款
            case "REFUND":
                //已关闭
            case "CLOSED":
                //已撤销(刷卡支付)
            case "REVOKED":
                //支付失败(其他原因，如银行返回失败)
            case "PAYERROR":
                //已接收，等待扣款
            case "ACCEPT":
                return vo.setOrderStatus(JmPayStatusConstant.FAIL).setPayTime(vo.getSuccessTime());
            default:
        }
        return vo;
    }

    public H5Service getService() {
        if (this.service == null) {
            this.initConfig();
        }
        return this.service;
    }
}
