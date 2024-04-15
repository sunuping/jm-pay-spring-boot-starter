package org.jm.pay.conf;

import org.jm.pay.config.JmAlipayConfig;
import org.jm.pay.config.JmWxConfig;
import org.jm.pay.impl.ali.JmAlipayApp;
import org.jm.pay.impl.ali.JmAlipayH5;
import org.jm.pay.impl.ali.JmAlipayPc;
import org.jm.pay.impl.wx.JmWxPayH5;
import org.jm.pay.impl.wx.JmWxPayJsapi;
import org.jm.pay.impl.wx.JmWxPayNative;
import org.jm.pay.impl.wx.JmWxPayPc;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author kong
 */
@AutoConfiguration
@ConditionalOnClass({JmAlipayConfig.class, JmWxConfig.class})
@EnableConfigurationProperties({JmAlipayConfig.class, JmWxConfig.class})
public class JmPayAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public JmAlipayApp jmAlipayApp(JmAlipayConfig jmAlipayConfig) {
        return new JmAlipayApp(jmAlipayConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public JmAlipayPc jmAlipayPc(JmAlipayConfig jmAlipayConfig) {
        return new JmAlipayPc(jmAlipayConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public JmAlipayH5 jmAlipayH5(JmAlipayConfig jmAlipayConfig) {
        return new JmAlipayH5(jmAlipayConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public JmWxPayH5 jmWxPayH5(JmWxConfig jmWxConfig) {
        return new JmWxPayH5(jmWxConfig);
    }


    @Bean
    @ConditionalOnMissingBean
    public JmWxPayNative jmWxPayNative(JmWxConfig jmWxConfig) {
        return new JmWxPayNative(jmWxConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public JmWxPayJsapi jmWxPayJsapi(JmWxConfig jmWxConfig) {
        return new JmWxPayJsapi(jmWxConfig);
    }


    @Bean
    @ConditionalOnMissingBean
    public JmWxPayPc jmWxPayPc(JmWxConfig jmWxConfig) {
        return new JmWxPayPc(jmWxConfig);
    }


}
