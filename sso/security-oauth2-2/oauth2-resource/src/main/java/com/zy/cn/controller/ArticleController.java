package com.zy.cn.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: application
 * @description:
 * @author: 狗蛋
 * @create: 2019-11-02 14:54
 */
@RestController
public class ArticleController {
    /***
     * 在此为了方便省略类资源服务器的增删改查
     * @return
     */

    @GetMapping("/")
    public String test(){
        return "访问资源服务器～～～0";
    }

    @GetMapping("/role")
    @PreAuthorize("hasAuthority('System')")
    public String perm(){
        return "访问资源服务器～～～1";
    }

    @GetMapping("/admin")
    // 该注解标注如果用户没有此权限就不可以访问·
    @PreAuthorize("hasAuthority('SystemContentDelete')")
    public String has(){
        return "访问资源服务器～～～2";
    }

    @GetMapping("/user")
    public Authentication getUser(Authentication authentication){
        return authentication;
    }


}