package org.jm.pay.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jm.pay.bean.pay.JmPayCallbackVO;
import org.jm.pay.constant.JmPayPlatformConstant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * @author kong
 */
@Slf4j
public class JmPayCallback {
    private final JmWxConfig jmWxConfig;

    public JmPayCallback(JmWxConfig jmWxConfig) {
        this.jmWxConfig = jmWxConfig;
    }

    public JmPayCallbackVO wxPayCallback(String callbackStr) {
        JSONObject wxCallbackDataJson = JSON.parseObject(callbackStr);
        JSONObject resource = wxCallbackDataJson.getJSONObject("resource");
        String ciphertext = resource.getString("ciphertext");
        String associatedData = resource.getString("associated_data");
        String nonce = resource.getString("nonce");

        AesUtil aesUtil = new AesUtil(this.jmWxConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        String decryptResource;
        try {
            decryptResource = aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8), nonce.getBytes(StandardCharsets.UTF_8), ciphertext);
        } catch (GeneralSecurityException | IOException e) {
            log.error("微信支付回调数据异常", e);
            return new JmPayCallbackVO();
        }
        log.debug("微信回调数据:{}", decryptResource);
        JSONObject resourceJson = JSON.parseObject(decryptResource);
        return new JmPayCallbackVO().setPayPlatform(JmPayPlatformConstant.WX).setOrderNo(resourceJson.getString("out_trade_no"))
                .setOutTradeNo(resourceJson.getString("transaction_id")).setPayTime(resourceJson.getString("success_time"));
    }

    public JmPayCallbackVO aliPayCallback(Map<String, String[]> requestParams) {

        return new JmPayCallbackVO()
                .setPayPlatform(JmPayPlatformConstant.ALI)
                //商家订单号
                .setOutTradeNo(requestParams.get("trade_no")[0])
                //订单号
                .setOrderNo(requestParams.get("out_trade_no")[0])
                //支付时间
                .setPayTime(requestParams.get("gmt_payment")[0]);
    }

//        boolean is;
//        try {
//            is = AlipaySignature.rsaCheckV1(dataMap, jmAlipayConfig.getAlipayPublicKey(), jmAlipayConfig.getCharset(), jmAlipayConfig.getSignType());
//        } catch (AlipayApiException e) {
//            log.error("支付宝支付回调效验失败 ", e);
//            return new JmPayCallbackVO();
//        }
//        if (is) {
//            if (log.isDebugEnabled()) {
//                log.debug("回调数据转为map {}", JSON.toJSONString(dataMap));
//            }
//            return new JmPayCallbackVO()
//                    .setPayPlatform(JmPayPlatformConstant.ALI)
//                    //商家订单号
//                    .setOutTradeNo(dataMap.get("trade_no"))
//                    //订单号
//                    .setOrderNo(dataMap.get("out_trade_no"))
//                    //支付时间
//                    .setPayTime(dataMap.get("gmt_payment"));
//        }
//        return new JmPayCallbackVO();

}
