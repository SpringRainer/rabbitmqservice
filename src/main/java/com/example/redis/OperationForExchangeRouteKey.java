package com.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/30 14:45
 * redis维护交换机和routekey
 */
@Service
public class OperationForExchangeRouteKey {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean existER(String exchange, String routekey) {
        // 维护交换机和routekey
        boolean flag = false;
        if (getRedisTemplate().opsForList().size(exchange) != null) {
            Long size = getRedisTemplate().opsForList().size(exchange);

            // 判断是否已存在值
            for (int i = 0; i < Objects.requireNonNull(size).intValue(); i++) {
                if (getRedisTemplate().opsForList().index(exchange, i).equals(routekey)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
