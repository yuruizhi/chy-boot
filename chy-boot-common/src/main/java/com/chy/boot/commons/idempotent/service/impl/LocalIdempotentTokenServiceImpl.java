package com.chy.boot.commons.idempotent.service.impl;

import com.chy.boot.commons.idempotent.service.IdempotentTokenService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 本地内存实现的幂等性Token服务
 * 用于测试环境或不依赖Redis的场景
 *
 * @author YuRuizhi
 */
@Service
@Primary
@ConditionalOnProperty(name = "spring.autoconfigure.exclude", havingValue = "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration")
public class LocalIdempotentTokenServiceImpl implements IdempotentTokenService {

    /**
     * 使用Caffeine缓存存储Token
     */
    private final Cache<String, String> tokenCache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS) // 默认1小时过期
            .maximumSize(10000)
            .build();

    @Override
    public String createToken(String prefix, long timeout, TimeUnit timeUnit) {
        // 生成唯一的Token
        String token = UUID.randomUUID().toString().replace("-", "");
        // 构建缓存键
        String key = buildKey(prefix, token);
        // 存储到缓存，值就是Token本身，用于验证
        tokenCache.put(key, token);
        
        // 注意：Caffeine不支持对单个键设置过期时间，这里使用了默认的过期策略
        // 如果需要更精细的控制，可以记录过期时间自行判断
        
        return token;
    }

    @Override
    public boolean validateToken(String prefix, String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        // 构建缓存键
        String key = buildKey(prefix, token);
        // 从缓存获取Token
        String value = tokenCache.getIfPresent(key);
        // 验证Token是否存在且与传入的Token一致
        return token.equals(value);
    }

    @Override
    public boolean deleteToken(String prefix, String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        // 构建缓存键
        String key = buildKey(prefix, token);
        // 先检查Token是否存在
        String value = tokenCache.getIfPresent(key);
        if (token.equals(value)) {
            // 存在则删除并返回成功
            tokenCache.invalidate(key);
            return true;
        }
        return false;
    }

    /**
     * 构建缓存键
     *
     * @param prefix 前缀
     * @param token Token
     * @return 缓存键
     */
    private String buildKey(String prefix, String token) {
        return "idempotent:" + prefix + ":" + token;
    }
} 