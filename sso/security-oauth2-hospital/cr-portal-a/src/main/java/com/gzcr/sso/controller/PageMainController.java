package com.gzcr.sso.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @program: gzcr-sso
 * @description: dadasdasda
 * @author:
 * @create: 2019-11-21
 */
@Controller
public class PageMainController {

    @GetMapping("/")
    public String redirectPageIndexMain(){
        return "list";
    }

    @GetMapping("/info")
    @ResponseBody
    public Principal info(Principal principal) {
        return principal;
    }

    @GetMapping("/me")
    @ResponseBody
    public Authentication me(Authentication authentication) {
        return authentication;
    }
}