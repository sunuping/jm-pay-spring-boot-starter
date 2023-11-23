package org.jm.pay.impl.ali;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.jm.pay.bean.pay.JmPayParam;
import org.jm.pay.bean.pay.JmPayVO;
import org.jm.pay.bean.query.JmOrderQueryParam;
import org.jm.pay.bean.query.JmOrderQueryVO;
import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.i.JmAlipay;

import java.util.Optional;

/**
 * @author kong
 */
@Slf4j
public class JmAlipayApp implements JmAlipay {
    private final JmAlipayConfig config;

    public JmAlipayApp(JmAlipayConfig config) {
        this.config = config;
    }

    @Override
    public void initConfig() {

    }

    @Override
    public JmPayVO pay(JmPayParam param) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(param.getDesc());
        model.setSubject(param.getOrderName());
        model.setOutTradeNo(param.getOrderNo());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(param.getAmount().toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        // 设置异步通知地址
        request.setNotifyUrl(Optional.ofNullable(param.getNotifyUrl()).orElse(config.getNotifyUrl()));
        try {
            AlipayTradeAppPayResponse response = this.config.getClient().sdkExecute(request);
            return new JmPayVO().setResponse(response.getBody());
        } catch (AlipayApiException e) {
            log.error("", e);
            return new JmPayVO().setErr(JSON.toJSONString(e));
        }
    }

    @Override
    public Boolean close(String oid) {
        return null;
    }

    @Override
    public JmOrderQueryVO query(JmOrderQueryParam param) {
        return null;
    }
}
