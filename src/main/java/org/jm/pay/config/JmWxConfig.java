package org.jm.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author kong
 */
@Component
@ConfigurationProperties(prefix = "jm.wx")
@Data
public class JmWxConfig {
    private String apiV3Key;
    private String privateKeyPath;
    private String mchId;
    private String appid;
    private String mchSerialNumber;
    /**
     * 通知地址
     */
    private String notifyUrl;
}
