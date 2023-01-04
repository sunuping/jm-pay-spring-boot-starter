package org.jm.pay.impl.wx;

import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.bean.query.JmOrderQueryParam;
import org.jm.pay.bean.query.JmOrderQueryVO;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.i.JmWxPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kong
 */
@Service
public class JmWxPayPc implements JmWxPay {
    private final JmWxConfig config;

    @Autowired
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
    public JmOrderQueryVO query(JmOrderQueryParam param) {
        return null;
    }
}
