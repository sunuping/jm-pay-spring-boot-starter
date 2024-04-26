package org.jm.pay.bean.transfer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author sunup
 */
@Data
@Accessors(chain = true)
public class JmTransferParam {
    //支付宝转账-----------------------------------------------------------------------------
    /**
     * 订单号
     */
    private String oid;
    /**
     * 订单标题
     */
    private String orderTitle;
    /**
     * 支付宝会员标识类型
     */
    private String identityType;
    /**
     * 支付宝会员标识
     */
    private String identity;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 金额
     */
    private BigDecimal amount;

}
