package org.jm.pay.bean.transfer;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author sunup
 */
@Data
@Accessors(chain = true)
public class JmTransferVO {
    //支付宝
    /**
     * 商户订单号
     */
    private String outBizNo;
    /**
     * 支付宝转账订单号
     */
    private String orderId;

    /**
     * 支付宝支付资金流水号
     */
    private String payFundOrderId;

    /**
     * 订单支付时间 格式为yyyy-MM-dd HH:mm:ss
     */
    private String transDate;
    /**
     * 转账单据状态。 SUCCESS（该笔转账交易成功）：成功； FAIL：失败（具体失败原因请参见error_code以及fail_reason返回值）；
     */
    private String status;
}
