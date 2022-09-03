package com.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/8/26 22:19
 */

@Controller
public class ShiroLoginPageController {
    @RequestMapping("/login1")
    public String login1(@RequestParam Map<String, String> map) {
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(map.get("username"));
        token.setPassword(map.get("password").toCharArray());

        SecurityUtils.getSubject().login(token);

        System.out.println(map.get("username")+"  "+map.get("password"));

        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "login";
    }
}
