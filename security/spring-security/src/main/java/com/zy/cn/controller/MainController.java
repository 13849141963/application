package com.zy.cn.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class MainController {

//    登录页面
    @RequestMapping("/userLogin")
    public String login(){
        return "login";
    }
// 跳转到无权限页面
    @RequestMapping("/error")
    public String error(){
        return "error";
    }

    @RequestMapping("/imageCode")
    @ResponseBody
    public String getAuthCode(HttpServletRequest request,HttpServletResponse response, HttpSession session)
            throws IOException {
        session.setAttribute("imageCode","1234");
        return "1234";
    }

}