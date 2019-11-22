package com.zy.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: application
 * @description: 登陆相关
 * @author: 狗蛋
 * @create: 2019-10-30 15:20
 */
@Controller
public class LoginController {
    /**
     * 跳转到登陆页面
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/user/me")
    public String selectByPrimaryKey(){
        return "login";
    }

}