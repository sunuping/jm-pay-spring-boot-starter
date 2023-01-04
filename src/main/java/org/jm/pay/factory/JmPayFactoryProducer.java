package org.jm.pay.factory;

import org.jm.pay.constant.JmPayTypeConstant;

/**
 * @author kong
 */
public class JmPayFactoryProducer {
    public static JmPayAbstractFactory getFactory(String payType) {
        switch (payType) {
            case JmPayTypeConstant.WX_PAY:
                return new JmWxFactory();
            case JmPayTypeConstant.ALIPAY:
                return new JmAlipayFactory();
            default:
                return null;
        }
    }


}
