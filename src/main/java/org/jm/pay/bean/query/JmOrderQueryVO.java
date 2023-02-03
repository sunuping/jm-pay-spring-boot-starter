package org.jm.pay.bean.query;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pay.java.service.payments.model.PromotionDetail;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.model.TransactionAmount;
import com.wechat.pay.java.service.payments.model.TransactionPayer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kong
 */
@Data
@Accessors(chain = true)
public class JmOrderQueryVO {
    private TransactionAmount amount;
    private String appid;
    private String attach;
    private String bankType;
    private String mchid;

    private TransactionPayer payer;
    private List<PromotionDetail> promotionDetail;
    private String successTime;
    private Transaction.TradeStateEnum tradeState;
    private String tradeStateDesc;
    private Transaction.TradeTypeEnum tradeType;
    private String transactionId;

    private JSONObject alipayOrderQueryVO;


    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 第三方订单号
     */
    private String outTradeNo;

    /**
     * 支付时间
     */
    private String payTime;
}
