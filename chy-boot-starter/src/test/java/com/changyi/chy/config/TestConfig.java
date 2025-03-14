package com.changyi.chy.config;

import com.changyi.chy.redis.util.RedisUtils;
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
     * 模拟RedisUtils
     */
    @Bean
    @Primary
    public RedisUtils redisUtils() {
        return Mockito.mock(RedisUtils.class);
    }
    
    // 可以根据需要添加更多的模拟依赖
} 