package com.chy.boot.commons.idempotent.service.impl;

import com.chy.boot.commons.idempotent.service.IdempotentTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis实现的幂等性Token服务
 *
 * @author YuRuizhi
 */
@Service
@ConditionalOnBean(RedisTemplate.class)
public class RedisIdempotentTokenServiceImpl implements IdempotentTokenService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Redis键前缀
     */
    private static final String KEY_PREFIX = "idempotent:";

    /**
     * Lua脚本：检查并删除键，保证原子性
     */
    private static final String CHECK_AND_DELETE_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) " +
                    "else return 0 end";

    @Override
    public String createToken(String prefix, long timeout, TimeUnit timeUnit) {
        // 生成唯一的Token
        String token = UUID.randomUUID().toString().replace("-", "");
        // 构建Redis键
        String key = buildKey(prefix, token);
        // 存储到Redis，值就是Token本身，用于验证
        redisTemplate.opsForValue().set(key, token, timeout, timeUnit);
        return token;
    }

    @Override
    public boolean validateToken(String prefix, String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        // 构建Redis键
        String key = buildKey(prefix, token);
        // 从Redis获取Token
        Object value = redisTemplate.opsForValue().get(key);
        // 验证Token是否存在且与传入的Token一致
        return token.equals(value);
    }

    @Override
    public boolean deleteToken(String prefix, String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        // 构建Redis键
        String key = buildKey(prefix, token);
        
        // 使用Lua脚本保证检查和删除操作的原子性
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(CHECK_AND_DELETE_SCRIPT);
        script.setResultType(Long.class);
        
        Long result = redisTemplate.execute(script, Collections.singletonList(key), token);
        return result != null && result > 0;
    }

    /**
     * 构建Redis键
     *
     * @param prefix 前缀
     * @param token Token
     * @return Redis键
     */
    private String buildKey(String prefix, String token) {
        return KEY_PREFIX + prefix + ":" + token;
    }
} 