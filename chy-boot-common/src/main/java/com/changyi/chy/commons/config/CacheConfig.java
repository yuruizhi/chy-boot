package com.changyi.chy.commons.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 缓存配置类
 * 配置多级缓存策略
 *
 * @author YuRuizhi
 */
@EnableCaching
@Configuration
public class CacheConfig {

    /**
     * 配置本地缓存管理器
     */
    @Bean("caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        
        // 短期缓存（30秒）
        CaffeineCache shortTermCache = new CaffeineCache("shortTerm", 
                Caffeine.newBuilder()
                        .expireAfterWrite(30, TimeUnit.SECONDS)
                        .maximumSize(1000)
                        .build());
        
        // 中期缓存（5分钟）
        CaffeineCache mediumTermCache = new CaffeineCache("mediumTerm", 
                Caffeine.newBuilder()
                        .expireAfterWrite(5, TimeUnit.MINUTES)
                        .maximumSize(500)
                        .build());
                        
        // 长期缓存（30分钟）
        CaffeineCache longTermCache = new CaffeineCache("longTerm", 
                Caffeine.newBuilder()
                        .expireAfterWrite(30, TimeUnit.MINUTES)
                        .maximumSize(200)
                        .build());
                        
        // 用户缓存（10分钟）
        CaffeineCache userCache = new CaffeineCache("userCache", 
                Caffeine.newBuilder()
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        .maximumSize(1000)
                        .recordStats() // 启用统计
                        .build());
        
        cacheManager.setCaches(Arrays.asList(
                shortTermCache, 
                mediumTermCache, 
                longTermCache,
                userCache
        ));
        
        return cacheManager;
    }
    
    /**
     * 配置Redis缓存管理器
     */
    @Primary
    @Bean("redisCacheManager")
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        // 默认配置
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        
        // 自定义配置不同的缓存空间
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withCacheConfiguration("shortTerm", 
                        defaultConfig.entryTtl(Duration.ofMinutes(5)))
                .withCacheConfiguration("mediumTerm", 
                        defaultConfig.entryTtl(Duration.ofHours(1)))
                .withCacheConfiguration("longTerm", 
                        defaultConfig.entryTtl(Duration.ofHours(24)))
                .withCacheConfiguration("userCache", 
                        defaultConfig.entryTtl(Duration.ofHours(2)));
        
        return builder.build();
    }
} 