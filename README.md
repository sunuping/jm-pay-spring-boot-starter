

# jm-pay-spring-boot-starter

### 项目简介
`jm-pay-spring-boot-starter` 是一个基于 Spring Boot 的支付模块集成工具，提供了对微信支付和支付宝支付的便捷集成。它简化了支付功能的开发，支持多种支付方式，适用于快速构建支付场景的 Java 应用。

#### 项目打包环境
- **Maven 版本**: 3.9.6  
- **Java 版本**: GraalVM Java 21  
- **Spring Boot 版本**: 3.2.4  

#### 已支持的功能
1. 微信支付：
   - Native 支付
   - H5 支付
   - JSAPI 支付
2. 支付宝支付：
   - PC 支付
   - App 支付
   - H5 支付
   - 转账功能
   - 支付宝证书配置支持

---

## 快速开始

#### Maven 依赖配置
在项目的 `pom.xml` 中添加以下依赖：

```xml
<!-- 支付 -->
<dependency>
    <groupId>com.alipay.sdk</groupId>
    <artifactId>alipay-sdk-java</artifactId>
    <version>4.39.52.ALL</version>
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
    <version>4.0.7</version>
</dependency>
```

---

## 配置文件

在 `application.yml` 中配置支付参数：

```yaml
jm:
  ali:
    appid: xxx  # 应用ID
    rsa-privateKey: xxx  # 应用私钥
    notify-url: https://xxx/pay/callback  # 支付异步通知地址
    return-url: https://xxx  # 支付同步返回地址
    gateway-url: https://openapi.alipay.com/gateway.do  # 支付宝网关地址
    charset: UTF-8
    format: json
    alipay-publicKey: xxx  # 支付宝公钥
    sign-type: RSA2  # 签名类型
    app-cert-path: 应用公钥证书路径  # 应用公钥证书路径
    alipay-cert-path: 支付宝公钥证书路径  # 支付宝公钥证书路径
    alipay-root-cert-path: 支付宝根证书路径  # 支付宝根证书路径
  wx:
    api-v3-key: xxx  # 微信支付 V3 密钥
    private-key-path: E:\xxxx\apiclient_key.pem  # 私钥文件路径
    private-key: xxxx  # 私钥内容
    mch-id: xxx  # 商户号
    appid: xxx  # 应用ID
    mch-serial-number: xxx  # 商户证书序列号
    notify-url: https://xxxx/pay/callback  # 支付异步通知地址
```

---

## 支付类型常量

为区分支付类型，提供了以下常量类：

#### 支付宝支付类型
```java
public class JmAlipayTypeConstant {
    public static final String ALIPAY_H5 = "alipay_h5";  // H5 支付
    public static final String ALIPAY_PC = "alipay_pc";  // PC 支付
    public static final String ALIPAY_APP = "alipay_app";  // App 支付
}
```

#### 微信支付类型
```java
public class JmWxPayTypeConstant {
    public static final String WX_H5 = "wx_h5";  // H5 支付
    public static final String WX_PC = "wx_pc";  // PC 支付
    public static final String WX_NATIVE = "wx_native";  // Native 支付
}
```

---

## 调用支付

以下是调用支付宝支付的示例代码：

### 示例代码
```java
@Service
public class AppRedServiceImpl {

    private final JmAlipayApp jmAlipayApp;

    @Autowired
    public AppRedServiceImpl(JmAlipayApp jmAlipayApp) {
        this.jmAlipayApp = jmAlipayApp;
    }

    /**
     * 发起支付宝支付
     *
     * @param orderId 订单号
     * @param amount 支付金额
     * @return 支付结果
     */
    public JmPayVO initiateAlipayPayment(String orderId, BigDecimal amount) {
        // 构造支付参数
        JmPayParam jmPayParam = new JmPayParam();
        jmPayParam.setOrderNo(orderId);       // 设置订单号
        jmPayParam.setAmount(amount);        // 设置支付金额
        jmPayParam.setDesc("充值");          // 设置描述信息
        jmPayParam.setOrderName("充值");     // 设置订单名称

        // 调用支付服务
        return jmAlipayApp.pay(jmPayParam);
    }
}
```

---

## 文档总结

1. **灵活集成**：该项目通过配置和常量的方式，让开发者快速集成微信支付和支付宝支付功能。
2. **简化调用**：通过封装的支付接口，简化了支付请求的构建和调用流程。
3. **支持多种支付场景**：支持 Native、H5、PC、App 支付等多种支付方式，满足不同业务需求。
