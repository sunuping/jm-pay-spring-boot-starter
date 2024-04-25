package org.jm.pay.constant;

/**
 * @author kong
 */
public class JmPayStatusConstant {
    /**
     * 支付成功
     */
    public static final int SUCCESS = 1;
    /**
     * 支付失败
     */
    public static final int FAIL = 0;
    /**
     * 未支付
     */
    public static final int NOT_PAY = -1;

    /**
     * 已过期 或者交易不存在
     */
    public static final int ALREADY_EXPIRE = 2;

    private JmPayStatusConstant() {
    }
}
