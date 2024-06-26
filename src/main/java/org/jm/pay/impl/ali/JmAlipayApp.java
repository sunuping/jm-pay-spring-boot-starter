package org.jm.pay.impl.ali;

import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.i.JmAlipay;

/**
 * @author kong
 */
@Getter
@Slf4j
public class JmAlipayApp extends JmBaseAlipay implements JmAlipay {
    private final JmAlipayConfig config;

    public JmAlipayApp(JmAlipayConfig config) {
        super(config);
        this.config = config;
    }

    @Override
    public JmPayVO pay(JmPayParam param) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //【描述】订单标题 注意：不可使用特殊字符，如 /，=，& 等。
        model.setSubject(param.getOrderName());
        //订单编号 由商家自定义，64个字符以内，仅支持字母、数字、下划线且需保证在商户端不重复
        model.setOutTradeNo(param.getOrderNo());
        //订单总金额 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]，金额不能为0
        model.setTotalAmount(param.getAmount().toString());
        request.setBizModel(model);
        try {
            AlipayTradeAppPayResponse response = this.config.getClient().sdkExecute(request);
            return new JmPayVO().setResponse(response.getBody());
        } catch (AlipayApiException e) {
            log.error("", e);
            return new JmPayVO().setErr(JSON.toJSONString(e));
        }
    }

}
