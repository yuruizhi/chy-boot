package com.changyi.chy.commons.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * JWT黑名单服务
 * 用于管理已失效的JWT令牌
 *
 * @author YuRuizhi
 */
@Service
public class JwtBlacklistService {

    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    /**
     * 将令牌加入黑名单
     *
     * @param token 令牌
     * @return 是否成功加入黑名单
     */
    public boolean addToBlacklist(String token) {
        try {
            // 计算剩余过期时间
            long expiration = jwtUtils.getExpirationTimeFromToken(token);
            long now = System.currentTimeMillis();
            long ttl = Math.max(expiration - now, 0);
            
            // 将令牌加入黑名单，并设置过期时间与令牌一致
            String key = BLACKLIST_PREFIX + token;
            redisTemplate.opsForValue().set(key, true, ttl, TimeUnit.MILLISECONDS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 检查令牌是否在黑名单中
     *
     * @param token 令牌
     * @return 是否在黑名单中
     */
    public boolean isBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        Boolean result = (Boolean) redisTemplate.opsForValue().get(key);
        return result != null && result;
    }
} 