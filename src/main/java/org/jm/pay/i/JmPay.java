package org.jm.pay.i;

import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.bean.query.JmOrderQueryParam;
import org.jm.pay.bean.query.JmOrderQueryVO;

/**
 * @author kong
 */
public interface JmPay {
    /**
     * 发起支付
     */
    JmPayVO pay(JmPayParam param);

    /**
     * 订单查询
     */
    JmOrderQueryVO query(JmOrderQueryParam param);

    Boolean close(String oid);

    void initConfig();
}
