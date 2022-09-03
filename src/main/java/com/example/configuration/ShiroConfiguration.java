package com.example.configuration;

import com.example.shiro.ShiroCacheManager;
import com.example.shiro.ShiroCachingRealm;
import com.example.shiro.ShiroRealm;
import com.example.shiro.ShiroSessionDao;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import sun.plugin.cache.CacheUpdateHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/8/26 21:41
 * shiro 配置类
 */

@Configuration
public class ShiroConfiguration {

    private SimpleCookie sessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie();
        Long sessionIdL = new Long(System.currentTimeMillis());
        String sessionId = sessionIdL.toString();
        cookie.setName(sessionId);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(10 * 60 * 1000);
        return cookie;
    }

    /**
     * 配置安全中心
     * @return defaultWebSecurityManager
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm
            , @Qualifier("sessionManager") SessionManager sessionManager
            , @Qualifier("ShiroCachingRealm") ShiroCachingRealm shiroCachingRealm, @Qualifier("shiroCacheManager") ShiroCacheManager shiroCacheManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        shiroRealm.setName("defaultRealm");
        defaultWebSecurityManager.setRealm(shiroRealm);
        defaultWebSecurityManager.setRealm(shiroCachingRealm);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        defaultWebSecurityManager.setCacheManager(shiroCacheManager);
        return defaultWebSecurityManager;
    }

    /**
     * 配置过滤器
     * @return shiroFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, String> filter = new HashMap<>();
        filter.put("/local/**", "authc");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filter);
        return shiroFilterFactoryBean;
    }

    /**
     * 定义sessionmanager 会话管理器 注册sessiondao
     */
    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager(@Qualifier("sessiondao") SessionDAO sessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());

        return sessionManager;
    }

    /**
     * 定义sessiondao 注册sessiondao
     */
    @Bean("sessiondao")
    public SessionDAO sessionDAO(@Qualifier("shiroRedisCacheManager") RedisCacheManager redisCacheManager) {
        return new ShiroSessionDao(new ShiroCacheManager(redisCacheManager));
    }

    @Bean("shiroRealm")
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCachingEnabled(true);
        shiroRealm.setAuthorizationCachingEnabled(true);
        shiroRealm.setAuthenticationCachingEnabled(true);
        return shiroRealm;
    }

    @Bean("ShiroCachingRealm")
    public ShiroCachingRealm shiroCachingRealm(@Qualifier("shiroCacheManager") ShiroCacheManager shiroCacheManager) {
        ShiroCachingRealm shiroCachingRealm = new ShiroCachingRealm();
        shiroCachingRealm.setCachingEnabled(true);
        shiroCachingRealm.setCacheManager(shiroCacheManager);
        return shiroCachingRealm;

    }
}