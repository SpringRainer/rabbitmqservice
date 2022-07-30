package com.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/30 16:34
 */
@Service
public class RedisForDuplicateConsume {
    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 维护消费者和deliverytag
    public boolean existER(String consumerTag, String deliveryTag) {
        // 维护交换机和routekey
        boolean flag = false;
        if (getRedisTemplate().opsForList().size(consumerTag) != null) {
            Long size = getRedisTemplate().opsForList().size(consumerTag);

            // 判断是否已存在值
            for (int i = 0; i < Objects.requireNonNull(size).intValue(); i++) {
                if (getRedisTemplate().opsForList().index(consumerTag, i).equals(deliveryTag)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
