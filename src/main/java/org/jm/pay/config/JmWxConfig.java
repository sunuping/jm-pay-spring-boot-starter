package org.jm.pay.config;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author kong
 */
@ConfigurationProperties(prefix = "jm.wx")
@Data
public class JmWxConfig {
    private String apiV3Key;
    private String privateKeyPath;
    private String privateKey;
    private String mchId;
    private String appid;
    private String mchSerialNumber;
    /**
     * 通知地址
     */
    private String notifyUrl;

    private Config config;

    public JmWxConfig() {
        System.out.println(".........");
    }

    public Config getConfig() {
        if (this.config == null) {
            // 初始化商户配置
            this.config = new RSAAutoCertificateConfig.Builder()
                    .merchantId(this.mchId)
//                .privateKeyFromPath(this.config.getPrivateKeyPath())
                    .merchantSerialNumber(this.mchSerialNumber)
                    .privateKey(this.privateKey)
                    .apiV3Key(this.apiV3Key)
                    .build();
        }
        return this.config;
    }

}
