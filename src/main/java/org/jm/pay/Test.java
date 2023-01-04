package org.jm.pay;

import lombok.extern.slf4j.Slf4j;
import org.jm.pay.constant.JmPayTypeConstant;
import org.jm.pay.factory.JmPayAbstractFactory;
import org.jm.pay.factory.JmPayFactoryProducer;

/**
 * @author kong
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
//        JmPayParam jmPayParam = new JmPayParam().setAmount(new BigDecimal("0.01"))
//                .setDesc("")
//                .setOrderNo(IdWorker.get32UUID())
//                .setDesc("会员升级")
//                .setOrderName("会员升级");
        JmPayAbstractFactory payFactory = JmPayFactoryProducer.getFactory(JmPayTypeConstant.ALIPAY);
//        JmAlipay jmAlipayH5 = Optional.ofNullable(payFactory).orElseThrow(() -> new ErrorException("支付类型错误")).getJmAlipay(JmAlipayTypeConstant.ALIPAY_H5);
//        jmAlipayH5.pay(jmPayParam);
//        log.debug("支付宝h5支付{}", jmAlipayH5.pay(jmPayParam));

//        JmAlipay jmAlipayPc = payFactory.getJmAlipay(JmAlipayTypeConstant.ALIPAY_PC);
//        log.debug("支付宝PC支付{}", jmAlipayPc.pay(jmPayParam));
//        log.info("支付宝订单查询{}", JSON.toJSONString(jmAlipayPc.query(new JmOrderQueryParam().setOrderNo("20221229181541608406386935861250"))));

//        JmAlipay jmAlipayPc = payFactory.getJmAlipay(JmAlipayTypeConstant.ALIPAY_APP);
//        log.debug("支付宝app支付{}", jmAlipayPc.pay(jmPayParam));


//        JmPayAbstractFactory payFactory = JmPayFactoryProducer.getFactory(JmPayTypeConstant.WX_PAY);
//        JmWxPay jmWxPay = payFactory.getJmWxPay(JmWxPayTypeConstant.WX_NATIVE);
//        log.debug("微信native支付{}", jmWxPay.pay(jmPayParam));

//        log.debug("微信商户订单查询{}", JSON.toJSONString(jmWxPay.query(new JmOrderQueryParam().setOrderNo("20221229180511608403762211762178"))));

    }
}
