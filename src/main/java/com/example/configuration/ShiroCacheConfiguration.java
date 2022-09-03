package com.example.configuration;

import com.example.shiro.ShiroCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/8/29 22:36
 */
@Configuration
public class ShiroCacheConfiguration {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean(name = "shiroCacheManager")
    public ShiroCacheManager shiroCacheManager() {
        RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig();
        //shiro配置的缓存管理器是这个，这里是设置登录的过期时间，及其session的过期时间，按秒为单位
        conf = conf.entryTtl(Duration.ofSeconds(300000));
        RedisCacheManager cacheManager = RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(conf)
                .build();
        return new ShiroCacheManager(cacheManager);
    }

    @Bean(name = "shiroRedisCacheManager")
    public RedisCacheManager redisCacheManager() {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        cacheConfig
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
        .disableCachingNullValues()
        .entryTtl(Duration.ofMinutes(10L));
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }
}
