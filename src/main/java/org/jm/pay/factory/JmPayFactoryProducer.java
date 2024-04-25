package org.jm.pay.factory;

import org.jm.pay.constant.JmPayTypeConstant;

/**
 * @author kong
 */
public class JmPayFactoryProducer {
    public static JmPayAbstractFactory getFactory(String payType) {
        return switch (payType) {
            case JmPayTypeConstant.WX_PAY -> new JmWxFactory();
            case JmPayTypeConstant.ALIPAY -> new JmAlipayFactory();
            default -> null;
        };
    }


}
