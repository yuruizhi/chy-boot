package com.changyi.chy.config;

import com.changyi.chy.commons.component.cache.RedisUtil;
import com.changyi.chy.demo.remote.AuthService;
import com.changyi.chy.demo.remote.HttpApi;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * 测试配置类
 * 提供测试环境中的模拟依赖配置
 *
 * @author YuRuizhi
 */
@TestConfiguration
@Profile("test")
public class TestConfig {
    
    /**
     * 模拟RedisUtil
     */
    @Bean
    @Primary
    public RedisUtil redisUtil() {
        return Mockito.mock(RedisUtil.class);
    }
    
    /**
     * 模拟HttpApi
     */
    @Bean
    @Primary
    public HttpApi httpApi() {
        return Mockito.mock(HttpApi.class);
    }
    
    /**
     * 模拟AuthService
     */
    @Bean
    @Primary
    public AuthService authService() {
        return Mockito.mock(AuthService.class);
    }
} 