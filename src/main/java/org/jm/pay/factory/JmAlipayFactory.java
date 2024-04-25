package org.jm.pay.factory;

import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.constant.JmAlipayTypeConstant;
import org.jm.pay.i.JmAlipay;
import org.jm.pay.i.JmWxPay;
import org.jm.pay.impl.ali.JmAlipayApp;
import org.jm.pay.impl.ali.JmAlipayH5;
import org.jm.pay.impl.ali.JmAlipayPc;

/**
 * @author kong
 */
public class JmAlipayFactory extends JmPayAbstractFactory {

    @Override
    public JmAlipay getJmAlipay(String jmPayType, JmAlipayConfig config) {
        return switch (jmPayType) {
            case JmAlipayTypeConstant.ALIPAY_H5 -> new JmAlipayH5(config);
            case JmAlipayTypeConstant.ALIPAY_PC -> new JmAlipayPc(config);
            case JmAlipayTypeConstant.ALIPAY_APP -> new JmAlipayApp(config);
            default -> null;
        };

    }

    @Override
    public JmWxPay getJmWxPay(String jmPayType, JmWxConfig jmWxConfig) {
        return null;
    }
}
