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
        switch (jmPayType) {
            case JmWxPayTypeConstant.WX_H5:
                return new JmWxPayH5(jmWxConfig);
            case JmWxPayTypeConstant.WX_PC:
                return new JmWxPayPc(jmWxConfig);
            case JmWxPayTypeConstant.WX_NATIVE:
                return new JmWxPayNative(jmWxConfig);
            case JmWxPayTypeConstant.WX_JSAPI:
                return new JmWxPayJsapi(jmWxConfig);
            default:
                return null;
        }
    }
}
