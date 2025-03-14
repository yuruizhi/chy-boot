package com.changyi.chy.commons.component.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * 多级缓存服务
 * 封装本地缓存和分布式缓存的操作
 *
 * @author YuRuizhi
 */
@Component
public class MultilevelCacheService {

    @Autowired
    @Qualifier("caffeineCacheManager")
    private CacheManager caffeineCacheManager;

    @Autowired
    @Qualifier("redisCacheManager")
    private CacheManager redisCacheManager;

    /**
     * 从缓存获取数据，优先从本地缓存获取，如果本地缓存没有，则从Redis缓存获取
     *
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param type 返回值类型
     * @param <T> 返回值泛型
     * @return 缓存数据
     */
    public <T> T get(String cacheName, Object key, Class<T> type) {
        // 优先从本地缓存获取
        Cache localCache = caffeineCacheManager.getCache(cacheName);
        if (localCache != null) {
            T value = localCache.get(key, type);
            if (value != null) {
                return value;
            }
        }

        // 本地缓存未命中，从Redis获取
        Cache redisCache = redisCacheManager.getCache(cacheName);
        if (redisCache != null) {
            T value = redisCache.get(key, type);
            if (value != null && localCache != null) {
                // 将Redis中的数据放入本地缓存
                localCache.put(key, value);
            }
            return value;
        }

        return null;
    }

    /**
     * 获取数据，如果缓存中没有，则通过valueLoader加载，并同时更新本地缓存和Redis缓存
     *
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param valueLoader 数据加载器
     * @param <T> 返回值泛型
     * @return 缓存数据
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String cacheName, Object key, Callable<T> valueLoader) {
        // 优先从本地缓存获取
        Cache localCache = caffeineCacheManager.getCache(cacheName);
        if (localCache != null) {
            T value = localCache.get(key, valueLoader);
            if (value != null) {
                // 更新Redis缓存
                Cache redisCache = redisCacheManager.getCache(cacheName);
                if (redisCache != null) {
                    redisCache.put(key, value);
                }
                return value;
            }
        }

        // 本地缓存未命中或无法加载，从Redis获取
        Cache redisCache = redisCacheManager.getCache(cacheName);
        if (redisCache != null) {
            try {
                Cache.ValueWrapper valueWrapper = redisCache.get(key);
                if (valueWrapper != null) {
                    T value = (T) valueWrapper.get();
                    if (value != null && localCache != null) {
                        // 将Redis中的数据放入本地缓存
                        localCache.put(key, value);
                    }
                    return value;
                } else {
                    // Redis也没有，通过valueLoader加载
                    T value = valueLoader.call();
                    if (value != null) {
                        redisCache.put(key, value);
                        if (localCache != null) {
                            localCache.put(key, value);
                        }
                    }
                    return value;
                }
            } catch (Exception e) {
                throw new RuntimeException("加载缓存数据失败", e);
            }
        }

        // 没有配置缓存，直接通过valueLoader加载
        try {
            return valueLoader.call();
        } catch (Exception e) {
            throw new RuntimeException("加载数据失败", e);
        }
    }

    /**
     * 向缓存中更新数据
     *
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param value 缓存值
     */
    public void put(String cacheName, Object key, Object value) {
        // 更新本地缓存
        Cache localCache = caffeineCacheManager.getCache(cacheName);
        if (localCache != null) {
            localCache.put(key, value);
        }

        // 更新Redis缓存
        Cache redisCache = redisCacheManager.getCache(cacheName);
        if (redisCache != null) {
            redisCache.put(key, value);
        }
    }

    /**
     * 从缓存中移除数据
     *
     * @param cacheName 缓存名称
     * @param key 缓存键
     */
    public void evict(String cacheName, Object key) {
        // 从本地缓存移除
        Cache localCache = caffeineCacheManager.getCache(cacheName);
        if (localCache != null) {
            localCache.evict(key);
        }

        // 从Redis缓存移除
        Cache redisCache = redisCacheManager.getCache(cacheName);
        if (redisCache != null) {
            redisCache.evict(key);
        }
    }

    /**
     * 清空指定名称的缓存
     *
     * @param cacheName 缓存名称
     */
    public void clear(String cacheName) {
        // 清空本地缓存
        Cache localCache = caffeineCacheManager.getCache(cacheName);
        if (localCache != null) {
            localCache.clear();
        }

        // 清空Redis缓存
        Cache redisCache = redisCacheManager.getCache(cacheName);
        if (redisCache != null) {
            redisCache.clear();
        }
    }
} 