package org.jm.pay.factory;

import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.constant.JmWxPayTypeConstant;
import org.jm.pay.i.JmAlipay;
import org.jm.pay.i.JmWxPay;
import org.jm.pay.impl.wx.JmWxPayH5;
import org.jm.pay.impl.wx.JmWxPayJsapi;
import org.jm.pay.impl.wx.JmWxPayNative;
import org.jm.pay.impl.wx.JmWxPayPc;

/**
 * @author kong
 */
public class JmWxFactory extends JmPayAbstractFactory {
    @Override
    public JmAlipay getJmAlipay(String jmPayType, JmAlipayConfig config) {
        return null;
    }

    @Override
    public JmWxPay getJmWxPay(String jmPayType, JmWxConfig jmWxConfig) {
        return switch (jmPayType) {
            case JmWxPayTypeConstant.WX_H5 -> new JmWxPayH5(jmWxConfig);
            case JmWxPayTypeConstant.WX_PC -> new JmWxPayPc(jmWxConfig);
            case JmWxPayTypeConstant.WX_NATIVE -> new JmWxPayNative(jmWxConfig);
            case JmWxPayTypeConstant.WX_JSAPI -> new JmWxPayJsapi(jmWxConfig);
            default -> null;
        };
    }
}
