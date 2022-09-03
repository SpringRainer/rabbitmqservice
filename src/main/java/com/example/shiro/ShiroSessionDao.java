package com.example.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/8/29 22:26
 */
@Component
public class ShiroSessionDao extends EnterpriseCacheSessionDAO {

    @Resource(name = "shiroRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private static String staticSessionId = "";
    /**
     * @param
     * @method 提供缓存session的扩展方法，到时候用的时候，
     * 想用什么缓存session信息，直接注入对应的CacheManager就行
     */
    public ShiroSessionDao(CacheManager cacheManager) {
        super.setCacheManager(cacheManager);
    }

    @Override
    protected void doUpdate(Session session) {
        System.out.println("更新session");
        if (redisTemplate.hasKey("shiroCache::".concat(staticSessionId))) {
            System.out.println("shiroCache::".concat(staticSessionId));
        }
        redisTemplate.opsForValue().set("shiroCache::".concat(staticSessionId), session, 10, TimeUnit.SECONDS);
    }

    @Override
    protected void doDelete(Session session) {

        System.out.println("删除session: "+session.toString());
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);
        staticSessionId = sessionId.toString();
        System.out.println("创建session: "+sessionId.toString());
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = null;

        System.out.println("读取session");
        session = (Session) redisTemplate.opsForValue().get(sessionId);
        System.out.println("反序列化session : "+session);
        return session;
    }
}
