package org.jm.pay.impl.wx;

import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.bean.query.JmOrderQueryParam;
import org.jm.pay.bean.query.JmOrderQueryVO;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.i.JmWxPay;

/**
 * @author kong
 */
public class JmWxPayPc implements JmWxPay {
    private final JmWxConfig config;

    public JmWxPayPc(JmWxConfig config) {
        this.config = config;
    }

    @Override
    public void initConfig() {

    }

    @Override
    public JmPayVO pay(JmPayParam param) {
        return null;
    }

    @Override
    public Boolean close(String oid) {
        return null;
    }

    @Override
    public JmOrderQueryVO query(JmOrderQueryParam param) {
        return null;
    }
}
