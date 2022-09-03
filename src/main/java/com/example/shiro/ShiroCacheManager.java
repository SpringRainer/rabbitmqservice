package com.example.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/8/29 22:31
 */
public class ShiroCacheManager implements CacheManager {

    private static RedisCacheManager redisCacheManager;
    public ShiroCacheManager(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new ShiroCache<>(redisCacheManager, "shiroCache");
    }
}
