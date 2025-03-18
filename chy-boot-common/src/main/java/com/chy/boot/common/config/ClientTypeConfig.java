package com.chy.boot.common.config;

import com.chy.boot.common.commons.interceptor.AdminRequireInterceptor;
import com.chy.boot.common.commons.interceptor.ClientTypeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 客户端类型配置
 *
 * @author YuRuizhi
 * @date 2024/3/18
 */
@Configuration
public class ClientTypeConfig implements WebMvcConfigurer {

    @Bean
    public ClientTypeInterceptor clientTypeInterceptor() {
        return new ClientTypeInterceptor();
    }
    
    @Bean
    public AdminRequireInterceptor adminRequireInterceptor() {
        return new AdminRequireInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册客户端类型拦截器（优先级高）
        registry.addInterceptor(clientTypeInterceptor())
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**") // 排除Swagger相关路径
                .order(0); // 设置优先级，数字越小优先级越高
        
        // 注册管理员权限拦截器（优先级低）
        registry.addInterceptor(adminRequireInterceptor())
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**") // 排除Swagger相关路径
                .order(10); // 在客户端类型拦截器之后执行
    }
} 