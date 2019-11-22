package com.gzcr.sso.config.security;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: application
 * @description: 撤销访问令牌的控制器-目的：就是为了注销时候删除有效令牌
 * @author:
 * @create: 2019-11-21
 */
@RestController
public class BackOutAccessTokenController {


    /***
     * 客户端发请求到此接口进行单点退出，清楚当前用户，并清楚cookie，access_token
     * @param request
     * @param response
     * 单点退出的两种方案
     * https://blog.csdn.net/weixin_34401479/article/details/88899395
     */
    @RequestMapping("/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}