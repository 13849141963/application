package com.zy.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @program: application
 * @description:
 * @author: 狗蛋
 * @create: 2019-11-05 21:54
 */
@Controller
public class MemberController {



    @RequestMapping("/")
    public String home() {
        return "redirect:/member";
    }

    /***
     * 在这里通过这个页面来掩饰单点退出
     * @return
     */
    @RequestMapping(value = {"/member"})
    public String member() {
        return "member/list";
    }

}