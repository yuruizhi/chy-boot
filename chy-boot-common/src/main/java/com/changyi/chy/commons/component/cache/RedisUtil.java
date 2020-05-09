package com.changyi.chy.commons.component.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtil {
    
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            log.debug("异常：{}", e);
        }
        return result;
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @param expireTime 过期时间
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.debug("异常：{}", e);
        }
        return result;
    }

    public boolean set(final String key, Object value, Long expireTime, final TimeUnit unit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, unit);
            result = true;
        } catch (Exception e) {
            log.debug("异常：{}", e);
        }
        return result;
    }


    /**
     * 写入缓存
     *
     * @param key 缓存key
     * @param hashKey hashKey
     * @param value 值
     * @return
     */
    public boolean put(final String key, String hashKey, Object value) {
        boolean result = false;
        try {
            HashOperations<Serializable, Object, Object> hash = redisTemplate.opsForHash();
            hash.put(key, hashKey, value);
            result = true;
        } catch (Exception e) {
            //e.printStackTrace();
            log.debug("异常：{}", e);
        }
        return result;
    }


    /**
     * 按hash方式读取缓存.
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object get(final String key, String hashKey) {
        HashOperations<Serializable, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    public Long increment(final String key, String hashKey,long delta) {
        HashOperations<Serializable, Object, Object> hash = redisTemplate.opsForHash();
        return hash.increment(key, hashKey,delta);
    }

}
