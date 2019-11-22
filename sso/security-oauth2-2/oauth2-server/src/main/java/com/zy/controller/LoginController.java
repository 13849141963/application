package com.zy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: application
 * @description: 客户端相关接口：调用资源服务器相关
 * @author: 狗蛋
 * @create: 2019-11-04 09:28
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}