package org.jm.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author kong
 */
@ConfigurationProperties(prefix = "jm.ali")
@Component
@Data
public class JmAlipayConfig {
    /**
     * 商户appid
     */
    private String appid;
    /**
     * 私钥 pkcs8格式的
     */
    private String rsaPrivateKey;
    /**
     * 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    private String notifyUrl;
    /**
     * 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
     */
    private String returnUrl;
    /**
     * 请求网关地址
     */
    private String gatewayUrl;
    /**
     * 编码
     */
    private String charset;
    /**
     * 返回格式
     */
    private String format;
    /**
     * 支付宝公钥
     */
    private String alipayPublicKey;
    /**
     * RSA2
     */
    private String signType;


}
