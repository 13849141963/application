package com.gzcr.sso.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: application
 * @description:
 * @author:
 * @create: 2019-11-21
 */
@RestController
public class HISResourceController {
    /***
     * 在此为了方便省略类资源服务器的增删改查
     * @return
     */

    @GetMapping("/")
    public String test(){
        return "访问资源服务器～～～his";
    }

    @GetMapping("/his")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String perm(){
        return "进入资源服务器～～～his";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Authentication getUser(Authentication authentication){
        return authentication;
    }


}