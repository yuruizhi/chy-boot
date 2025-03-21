package com.chy.boot.common.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 自定义请求映射处理程序映射配置
 *
 * <link>
 *     https://stackoverflow.com/questions/36744678/spring-boot-swagger-2-ui-custom-requestmappinghandlermapping-mapping-issue
 * </link>
 *
 * @author ZhangHao
 * @date 2021/01/14
 */
@Configuration
public class CustomRequestMappingHandlerMappingConfig {
    @Bean
    public WebMvcRegistrations webMvcRegistrationsHandlerMapping() {
        return new WebMvcRegistrations () {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new CustomRequestMappingHandlerMapping();
            }
        };
    }
}
