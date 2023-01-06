package org.jm.pay.api;

import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.constant.JmAlipayTypeConstant;
import org.jm.pay.constant.JmPayTypeConstant;
import org.jm.pay.factory.JmPayAbstractFactory;
import org.jm.pay.factory.JmPayFactoryProducer;
import org.jm.pay.i.JmAlipay;
import org.jm.pay.impl.ali.JmAlipayPc;
import org.jm.pay.impl.wx.JmWxPayNative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author kong
 */
@RestController
public class TestApi {
    private final JmWxPayNative jmWxPayNative;
    private final JmAlipayPc jmAliPayPc;
    @Autowired
    public TestApi(JmWxPayNative jmWxPayNative, JmAlipayPc jmAliPayPc) {
        this.jmAliPayPc = jmAliPayPc;
        this.jmWxPayNative = jmWxPayNative;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    @GetMapping("/test/ali/pc/pay")
    public JmPayVO test() {
        return this.jmAliPayPc.pay(this.getJmPayParam());
    }

    private JmPayParam getJmPayParam() {
        return new JmPayParam().setAmount(new BigDecimal("0.01"))
                .setDesc("")
                .setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""))
                .setDesc("会员升级")
                .setOrderName("会员升级");
    }


    @GetMapping("/test/wx/native/pay")
    public JmPayVO testWxPcPay() {
        return this.jmWxPayNative.pay(this.getJmPayParam());
    }

}
