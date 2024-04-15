package org.jm.pay.impl.ali;

import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import lombok.extern.slf4j.Slf4j;
import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.i.JmAlipay;

import java.util.Optional;

/**
 * @author kong
 */
@Slf4j
public class JmAlipayH5 extends JmBaseAlipay implements JmAlipay {
    private final JmAlipayConfig config;

    public JmAlipayH5(JmAlipayConfig config) {
        super(config);
        this.config = config;
    }


    @Override
    public JmPayVO pay(JmPayParam param) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(param.getOrderNo());
        model.setSubject(param.getOrderName());
        model.setTotalAmount(param.getAmount().toString());
        model.setBody(param.getDesc());
        model.setTimeoutExpress(param.getTimeout());
        model.setProductCode("QUICK_WAP_WAY");
        request.setBizModel(model);
        // 设置异步通知地址
        request.setNotifyUrl(Optional.ofNullable(param.getNotifyUrl()).orElse(config.getNotifyUrl()));
        // 设置同步地址
        request.setReturnUrl(Optional.ofNullable(param.getReturnUrl()).orElse(config.getReturnUrl()));
        try {
            // 调用SDK生成表单
            return new JmPayVO().setResponse(this.config.getClient().pageExecute(request).getBody());
        } catch (AlipayApiException e) {
            log.error("", e);
            return new JmPayVO().setErr(JSON.toJSONString(e));
        }
    }


}
