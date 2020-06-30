package com.changyi.chy.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 配置注册自定义的 CustomRequestMappingHandlerMapping.
 *
 * @author Henry.yu
 * @date 2020.6.30
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new CustomRequestMappingHandlerMapping();
    }

}