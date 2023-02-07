package org.jm.pay.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jm.pay.bean.pay.JmPayCallbackVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author kong
 */
@Service
@Slf4j
public class JmPayCallback {
    private final JmWxConfig jmWxConfig;
    private final JmAlipayConfig jmAlipayConfig;

    @Autowired
    public JmPayCallback(JmWxConfig jmWxConfig, JmAlipayConfig jmAlipayConfig) {
        this.jmWxConfig = jmWxConfig;
        this.jmAlipayConfig = jmAlipayConfig;
    }

    public JmPayCallbackVO getJmPayCallback(String callbackStr) throws AlipayApiException, GeneralSecurityException, IOException {
        JmPayCallbackVO callback  = this.aliPayCallback(callbackStr);
        if (StringUtils.isBlank(callback.getOrderNo())){
           return this.wxPayCallback(callbackStr);
        }
        return callback;
    }

    private JmPayCallbackVO wxPayCallback(String callbackStr) throws GeneralSecurityException, IOException {
        JSONObject wxCallbackDataJson = JSON.parseObject(callbackStr);
        JSONObject resource = wxCallbackDataJson.getJSONObject("resource");
        String ciphertext = resource.getString("ciphertext");
        String associatedData = resource.getString("associated_data");
        String nonce = resource.getString("nonce");

        AesUtil aesUtil = new AesUtil(this.jmWxConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        String decryptResource = aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8), nonce.getBytes(StandardCharsets.UTF_8), ciphertext);
        log.debug("微信回调数据:{}", decryptResource);
        JSONObject resourceJson = JSON.parseObject(decryptResource);
        return new JmPayCallbackVO().setOrderNo(resourceJson.getString("out_trade_no"))
                .setOutTradeNo(resourceJson.getString("transaction_id"));
    }

    private JmPayCallbackVO aliPayCallback(String callbackStr) throws AlipayApiException {
        Map<String, String> dataMap = new HashMap<>(100);
        String[] dataArr = callbackStr.split("&");
        for (String item : dataArr) {
            String[] arr = item.split("=");
            if (arr.length == 2) {
                dataMap.put(arr[0], Optional.ofNullable(arr[1]).orElse(""));
            }
        }

        boolean is = AlipaySignature.rsaCheckV1(dataMap, jmAlipayConfig.getAlipayPublicKey(), jmAlipayConfig.getCharset(), jmAlipayConfig.getSignType());
        if (is) {
            if (log.isDebugEnabled()) {
                log.debug("回调数据转为map {}", JSON.toJSONString(dataMap));
            }
        }
        return new JmPayCallbackVO().setOutTradeNo(dataMap.get("trade_no")).setOrderNo(dataMap.get("out_trade_no"));
    }

}
