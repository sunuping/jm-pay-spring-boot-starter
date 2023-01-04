package org.jm.pay.bean.query;

import com.wechat.pay.java.service.payments.model.PromotionDetail;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.model.TransactionAmount;
import com.wechat.pay.java.service.payments.model.TransactionPayer;
import lombok.Data;
import lombok.experimental.Accessors;

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
    private String outTradeNo;
    private TransactionPayer payer;
    private List<PromotionDetail> promotionDetail;
    private String successTime;
    private Transaction.TradeStateEnum tradeState;
    private String tradeStateDesc;
    private Transaction.TradeTypeEnum tradeType;
    private String transactionId;

    private String alipayOrderQueryVO;
}
