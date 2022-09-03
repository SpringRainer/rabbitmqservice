package com.example.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.CachingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/9/1 0:00
 */
public class ShiroCachingRealm extends CachingRealm {

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        System.out.println("执行缓存支持");
        return true;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("获取缓存中的授权信息");
        Object username = authenticationToken.getPrincipal();
        return new SimpleAuthenticationInfo("user", "123456".toCharArray(), getName());
    }
}
