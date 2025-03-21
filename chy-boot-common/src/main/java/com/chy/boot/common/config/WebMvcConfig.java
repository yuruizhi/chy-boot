package com.chy.boot.common.config;

import com.chy.boot.common.interceptor.ApiRequestLogInterceptor;
import com.chy.boot.common.interceptor.ExecuteContextInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC配置
 *
 * @author Henry.Yu
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册上下文拦截器
        registry.addInterceptor(executeContextInterceptor())
                .addPathPatterns("/**");

        // 注册日志拦截器
        registry.addInterceptor(apiRequestLogInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public ExecuteContextInterceptor executeContextInterceptor() {
        return new ExecuteContextInterceptor();
    }

    @Bean
    public ApiRequestLogInterceptor apiRequestLogInterceptor() {
        return new ApiRequestLogInterceptor();
    }
} 