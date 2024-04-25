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

    /**
     * 支付宝转账-----------------------------------------------------------------------------
     * 手机号
     */
    private String phone;
    /**
     * 真实姓名
     */
    private String actualName;
    /**
     * 商家侧唯一订单号，由商家自定义。对于不同转账请求，商家需保证该订单号在自身系统唯一。
     * 示例值：20190619000000001
     */
    private String out_biz_no;
    /**
     * 订单总金额，单位为元，不支持千位分隔符，精确到小数点后两位，取值范围[0.1,100000000]。
     * 注意：最小可传金额 0.1 元，最大可传金额 100000000 元，如超过最小或最大金额限制，可能会导致金额超限等接口报错。
     * 示例值：1.68
     */
    private BigDecimal trans_amount;
    /**
     * 销售产品码。单笔无密转账固定为 TRANS_ACCOUNT_NO_PWD。
     */
    private String product_code;
/**
 * 业务场景。单笔无密转账固定为 DIRECT_TRANSFER。
 */
    /**
     * 支付宝转账-----------------------------------------------------------------------------
     */
}
