package com.changyi.chy.web.xss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * XSS自动配置类
 *
 * @author YuRuizhi
 */
@Configuration
@EnableConfigurationProperties({XssProperties.class, XssUrlProperties.class})
@ConditionalOnProperty(prefix = "cy.xss", name = "enable", havingValue = "true", matchIfMissing = true)
public class XssAutoConfiguration {

    /**
     * XSS过滤器配置
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration(XssProperties xssProperties, XssUrlProperties xssUrlProperties) {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new XssFilter(xssProperties, xssUrlProperties));
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
} 