package org.jm.pay;

import lombok.extern.slf4j.Slf4j;
import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.constant.JmPayTypeConstant;
import org.jm.pay.constant.JmWxPayTypeConstant;
import org.jm.pay.factory.JmPayFactoryProducer;
import org.jm.pay.factory.JmWxFactory;
import org.jm.pay.impl.wx.JmWxPayH5;
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
    private final JmAlipayConfig jmAlipayConfig;
    private final JmWxConfig jmWxConfig;

    @Autowired
    public Test(JmAlipayConfig jmAlipayConfig, JmWxConfig jmWxConfig) {
        this.jmAlipayConfig = jmAlipayConfig;
        this.jmWxConfig = jmWxConfig;
    }

    @org.junit.jupiter.api.Test
    public void test() {
        String orderNo = UUID.randomUUID().toString().replaceAll("-", "");
        JmPayParam jmPayParam = new JmPayParam()
                .setAmount(new BigDecimal("0.01"))
                .setDesc("")
                .setOrderNo(orderNo)
                .setDesc("会员升级")
                .setOrderName("会员升级");

        JmWxFactory jmWxFactory = (JmWxFactory) JmPayFactoryProducer.getFactory(JmPayTypeConstant.WX_PAY);
        assert jmWxFactory != null;
        JmWxPayH5 jmWxPayH5 = (JmWxPayH5) jmWxFactory.getJmWxPay(JmWxPayTypeConstant.WX_H5, this.jmWxConfig);
    }

}
