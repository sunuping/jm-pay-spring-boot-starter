package org.jm.pay.bean.pay;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author kong
 */
@Data
@Accessors(chain = true)
public class JmPayParam {
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 订单名称
     */
    private String orderName;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 描述
     */
    private String desc;
    /**
     * 附加信息
     */
    private String attach;
    /**
     * 超时时间
     */
    private String timeout;

    /**
     * 回调通知地址
     */
    private String notifyUrl;
    /**
     * 支付成功同步返回地址
     */
    private String returnUrl;

    /**
     * 客户端ip
     */
    private String payerClientIp;

}
