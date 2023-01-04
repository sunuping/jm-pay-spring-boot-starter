package org.jm.pay.factory;


import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.i.JmAlipay;
import org.jm.pay.i.JmWxPay;

/**
 * @author kong
 */
public abstract class JmPayAbstractFactory {
    public abstract JmAlipay getJmAlipay(String jmPayType, JmAlipayConfig config);

    public abstract JmWxPay getJmWxPay(String jmPayType, JmWxConfig jmWxConfig);
}
