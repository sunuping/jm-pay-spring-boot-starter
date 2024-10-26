package org.jm.pay.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.bean.query.JmOrderQueryParam;
import org.jm.pay.bean.query.JmOrderQueryVO;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.constant.JmPayTypeConstant;
import org.jm.pay.constant.JmWxPayTypeConstant;
import org.jm.pay.factory.JmPayFactoryProducer;
import org.jm.pay.impl.ali.JmAlipayH5;
import org.jm.pay.impl.ali.JmAlipayPc;
import org.jm.pay.impl.wx.JmWxPayH5;
import org.jm.pay.impl.wx.JmWxPayNative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author kong
 */
@RestController
@Slf4j
public class TestApi {
    private final JmWxPayNative jmWxPayNative;
    private final JmAlipayPc jmAliPayPc;
    private final JmAlipayH5 jmAlipayH5;

    private final JmWxPayH5 jmWxPayH5;
    private final JmWxConfig jmWxConfig;

    @Autowired
    public TestApi(JmWxPayNative jmWxPayNative, JmAlipayPc jmAliPayPc, JmAlipayH5 jmAlipayH5, JmWxPayH5 jmWxPayH5, JmWxConfig jmWxConfig) {
        this.jmAliPayPc = jmAliPayPc;
        this.jmWxPayNative = jmWxPayNative;
        this.jmAlipayH5 = jmAlipayH5;
        this.jmWxPayH5 = jmWxPayH5;
        this.jmWxConfig = jmWxConfig;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    @GetMapping("/test/ali/pc/pay")
    public JmPayVO test() {
        return this.jmAliPayPc.pay(this.getJmPayParam());
    }

    private JmPayParam getJmPayParam() {
        String orderNo = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("订单号:{}", orderNo);
        return new JmPayParam().setAmount(new BigDecimal("0.01"))
                .setDesc("")
                .setOrderNo(orderNo)
                .setDesc("会员升级")
                .setOrderName("会员升级");
    }

    @GetMapping("/test/wx/h5/pay")
    public JmPayVO testWxH5Pay() {
        return this.jmWxPayH5.pay(this.getJmPayParam());
    }


    @GetMapping("/test/wx/native/pay")
    public JmPayVO testWxPcPay() {
        return this.jmWxPayNative.pay(this.getJmPayParam());
    }


    @GetMapping("/test/ali/pc/query")
    public void testGetOrderStatus() {
        JmOrderQueryVO orderQueryVO = this.jmAliPayPc.query(new JmOrderQueryParam().setOrderNo("20221229181541608406386935861250"));
//        JmOrderQueryVO orderQueryVO =  this.jmWxPayNative.query(new JmOrderQueryParam().setOrderNo("20230203145421621401695186137089"));
        log.info("支付宝订单查询{}", JSON.toJSONString(orderQueryVO));
    }

    @GetMapping("/test/ali/h5/pay")
    public JmPayVO test2() {
        return this.jmAlipayH5.pay(this.getJmPayParam());
    }

    @GetMapping("/test/wx/h5/query/{orderNo}")
    public JmOrderQueryVO testWxH5Query(@PathVariable("orderNo") String orderNo) {
        return this.jmWxPayH5.query(new JmOrderQueryParam().setOrderNo(orderNo));
    }

    @GetMapping("/test/ali/h5/pay")
    public JmPayVO test3() {
       JmPayFactoryProducer.getFactory(JmPayTypeConstant.WX_PAY).getJmWxPay(JmWxPayTypeConstant.WX_H5,jmWxConfig);

        return this.jmAlipayH5.pay(this.getJmPayParam());
    }



}
