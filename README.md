# jm-pay-spring-boot-starter

```text
打包环境
maven 3.9.6
graalvm java21 
spring boot3.2.4

集成微信和支付宝支付sdk<br>
已完成微信native h5<br>
已完成支付宝pc app h5<br>
已完成微信jsapi<br>
```


## 快速开始

```xml
		<!-- 支付-->
        <dependency>
            <groupId>com.alipay.sdk</groupId>
            <artifactId>alipay-sdk-java</artifactId>
            <version>4.39.40.ALL</version>
        </dependency>
        <dependency>
            <groupId>com.github.wechatpay-apiv3</groupId>
            <artifactId>wechatpay-java</artifactId>
            <version>0.2.12</version>
        </dependency>
        <dependency>
            <groupId>com.github.wechatpay-apiv3</groupId>
            <artifactId>wechatpay-apache-httpclient</artifactId>
            <version>0.4.9</version>
        </dependency>
        <dependency>
            <groupId>org.jm</groupId>
            <artifactId>jm-pay-spring-boot-starter</artifactId>
            <version>4.0.2</version>
        </dependency>

```
## application.yml
```yaml
jm:
  ali:
    appid: xxx
    rsa-privateKey: xxx
    notify-url: https://xxx/pay/callback
    return-url: https://xxx
    gateway-url: https://openapi.alipay.com/gateway.do
    charset: UTF-8
    format: json
    alipay-publicKey: xxx
    sign-type: RSA2
  wx:
    api-v3-key: xxx
    private-key-path: E:\xxxx\apiclient_key.pem
    private-key: xxxx
    mch-id: xxx
    appid: xxx
    mch-serial-number: xxx
    notify-url: https://xxxx/pay/callback

```

## 支付类型
```java
/**
 * @author kong
 */
public class JmAlipayTypeConstant {
    /**
     * 支付宝h5支付
     */
    public static final String ALIPAY_H5 = "alipay_h5";
    /**
     * 支付宝pc支付
     */
    public static final String ALIPAY_PC = "alipay_pc";
    /**
     * 支付宝app支付
     */
    public static final String ALIPAY_APP = "alipay_app";
}

/**
 * @author kong
 */
public class JmWxPayTypeConstant {
    /**
     * 微信h5支付
     */
    public static final String WX_H5 = "wx_h5";
    /**
     * 微信pc支付
     */
    public static final String WX_PC = "wx_pc";
    /**
     * 微信native支付
     */
    public static final String WX_NATIVE = "wx_native";
}

```

## 调用支付
```java
    private final JmAlipayApp jmAlipayApp;

    @Autowired
    public AppRedServiceImpl(JmAlipayApp jmAlipayApp) {
        this.jmAlipayApp = jmAlipayApp;
    }

	JmPayParam jmPayParam = new JmPayParam();
    jmPayParam.setDesc("充值");
    jmPayParam.setAmount(param.getRedTotalMoney());
    jmPayParam.setOrderName("充值");
    jmPayParam.setOrderNo(orderId);
   	JmPayVO jmPayVO = this.jmAlipayApp.pay(jmPayParam);

```
