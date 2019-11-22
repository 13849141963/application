package com.zy.cn.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/index")
    public String selectByPrimaryKey(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }


    /**
     * 无权限页面
     * @return
     */
    @RequestMapping("/403")
    public String noAuthenticationPage(){
        return "403";
    }
}