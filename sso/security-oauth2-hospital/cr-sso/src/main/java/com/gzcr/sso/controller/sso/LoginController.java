package com.gzcr.sso.controller.sso;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: application
 * @description:
 * @author:
 * @create: 2019-11-21
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}