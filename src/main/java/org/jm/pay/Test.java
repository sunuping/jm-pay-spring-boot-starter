package org.jm.pay;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.query.JmOrderQueryParam;
import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.constant.JmAlipayTypeConstant;
import org.jm.pay.constant.JmPayTypeConstant;
import org.jm.pay.constant.JmWxPayTypeConstant;
import org.jm.pay.factory.JmAlipayFactory;
import org.jm.pay.factory.JmPayFactoryProducer;
import org.jm.pay.factory.JmWxFactory;
import org.jm.pay.impl.ali.JmAlipayApp;
import org.jm.pay.impl.wx.JmWxPayJsapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author kong
 */
@Slf4j
@SpringBootTest
public class Test {
    private  JmAlipayConfig jmAlipayConfig;
    private  JmWxConfig jmWxConfig;

    @Autowired
    public void setJmAlipayConfig(JmAlipayConfig jmAlipayConfig) {
        this.jmAlipayConfig = jmAlipayConfig;
    }
    @Autowired
    public void setJmWxConfig(JmWxConfig jmWxConfig) {
        this.jmWxConfig = jmWxConfig;
    }

    @org.junit.jupiter.api.Test
    public void test() {
        String orderNo = UUID.randomUUID().toString().replaceAll("-", "");
        JmPayParam jmPayParam = new JmPayParam()
                .setAmount(new BigDecimal("0.01"))
                .setDesc("")
                .setPayerClientIp("218.88.68.49")
                .setOrderNo(orderNo)
                .setDesc("充值")
                .setOpenid("ogcPSwjVvWzMsoerRNZedarsGvkU")
                .setOrderName("充值");

        JmAlipayFactory jmAlipayFactory = (JmAlipayFactory) JmPayFactoryProducer.getFactory(JmPayTypeConstant.ALIPAY);
        //微信
//        assert jmWxFactory != null;
////        JmWxPayH5 jmWxPayH5 = (JmWxPayH5) jmWxFactory.getJmWxPay(JmWxPayTypeConstant.WX_H5, this.jmWxConfig);
////        System.out.println(jmWxPayH5.pay(jmPayParam).getResponse());
//
//        JmWxPayJsapi jmWxPayJsapi = (JmWxPayJsapi) jmWxFactory.getJmWxPay(JmWxPayTypeConstant.WX_JSAPI, this.jmWxConfig);
//
////        System.out.println(jmWxPayJsapi.pay(jmPayParam).getResponse());
//
////        System.out.println(jmWxPayJsapi.close("84185ab11c474d078eb620952803ce5e"));
//        System.out.println(JSON.toJSONString(jmWxPayJsapi.query(new JmOrderQueryParam().setOrderNo("84185ab11c474d078eb620952803ce5e").setPayType(JmWxPayTypeConstant.WX_JSAPI))));

        assert jmAlipayFactory!=null;
        JmAlipayApp jmAlipayApp = (JmAlipayApp) jmAlipayFactory.getJmAlipay(JmAlipayTypeConstant.ALIPAY_APP,this.jmAlipayConfig);
        System.out.println(JSON.toJSONString(jmPayParam));
        System.out.println(jmAlipayApp.pay(jmPayParam));

        jmAlipayApp.query(new JmOrderQueryParam().setOrderNo("6b9ab1ea3328486f93e6556a543dfcfb").setPayType(JmAlipayTypeConstant.ALIPAY_APP));
    }

    public void testClose(){
        //84185ab11c474d078eb620952803ce5e

    }

}
