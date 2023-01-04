package org.jm.pay.bean.query;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author kong
 */
@Data
@Accessors(chain = true)
public class JmOrderQueryParam {
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 订单编号
     */
    private String orderNo;

}
