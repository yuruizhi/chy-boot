package com.changyi.chy.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.changyi.chy.commons.security.JwtAuthenticationFilter;
import com.changyi.chy.commons.security.RestAccessDeniedHandler;
import com.changyi.chy.commons.security.RestAuthenticationEntryPoint;

import java.util.Arrays;

/**
 * 安全配置类
 * 配置Spring Security相关设置
 *
 * @author YuRuizhi
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * 配置密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 配置安全过滤链
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, 
                                                  JwtAuthenticationFilter jwtAuthenticationFilter,
                                                  RestAccessDeniedHandler accessDeniedHandler,
                                                  RestAuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        return http
                // 禁用CSRF（前后端分离的项目不需要CSRF防护）
                .csrf(AbstractHttpConfigurer::disable)
                // 启用CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 基于JWT，不需要Session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 异常处理
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.accessDeniedHandler(accessDeniedHandler);
                    exceptionHandling.authenticationEntryPoint(authenticationEntryPoint);
                })
                // 设置权限
                .authorizeHttpRequests(requests -> requests
                        // 允许所有用户访问Swagger和API文档
                        .requestMatchers("/doc.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // 允许所有用户访问健康检查端点
                        .requestMatchers("/actuator/**").permitAll()
                        // 允许所有用户访问登录API
                        .requestMatchers("/auth/login", "/auth/refresh").permitAll()
                        // 允许所有用户访问H2控制台（仅用于开发环境）
                        .requestMatchers("/h2-console/**").permitAll()
                        // 其他所有请求需要认证
                        .anyRequest().authenticated()
                )
                // 在用户名密码过滤器前添加JWT过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 允许iframe加载H2控制台（仅用于开发环境）
                .headers(headers -> headers.frameOptions().sameOrigin())
                .build();
    }
    
    /**
     * 配置CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // 允许所有来源（生产环境应限制）
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
} 