package org.jm.pay.impl.wx;

import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.bean.query.JmOrderQueryParam;
import org.jm.pay.bean.query.JmOrderQueryVO;
import org.jm.pay.bean.transfer.JmTransferParam;
import org.jm.pay.bean.transfer.JmTransferVO;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.i.JmWxPay;

/**
 * @author kong
 */
public class JmWxPayPc implements JmWxPay {

    public JmWxPayPc(JmWxConfig config) {
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

    @Override
    public JmTransferVO transfer(JmTransferParam param) {
        return null;
    }
}
