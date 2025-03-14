package com.changyi.chy.security.config;

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

import com.changyi.chy.security.auth.JwtAuthenticationFilter;
import com.changyi.chy.security.auth.RestAccessDeniedHandler;
import com.changyi.chy.security.auth.RestAuthenticationEntryPoint;

/**
 * 安全配置
 *
 * @author YuRuizhi
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 安全过滤器链
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, 
                                                  JwtAuthenticationFilter jwtAuthenticationFilter,
                                                  RestAccessDeniedHandler accessDeniedHandler,
                                                  RestAuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        http
            // 禁用CSRF
            .csrf(AbstractHttpConfigurer::disable)
            // 基于Token，不需要Session
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 设置未授权和未登录处理
            .exceptionHandling(handling -> handling
                .accessDeniedHandler(accessDeniedHandler) // 授权异常处理
                .authenticationEntryPoint(authenticationEntryPoint) // 认证异常处理
            )
            // 设置权限
            .authorizeHttpRequests(authorize -> authorize
                // 以下接口不需要认证
                .requestMatchers("/api/auth/login", "/api/auth/refresh", "/api/auth/logout").permitAll()
                // Swagger相关接口不需要认证
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/doc.html/**", "/webjars/**").permitAll()
                // 静态资源不需要认证
                .requestMatchers("/favicon.ico", "/static/**").permitAll()
                // 健康检查接口不需要认证
                .requestMatchers("/actuator/**").permitAll()
                // 其他所有接口都需要认证
                .anyRequest().authenticated()
            )
            // 添加JWT过滤器
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
} 