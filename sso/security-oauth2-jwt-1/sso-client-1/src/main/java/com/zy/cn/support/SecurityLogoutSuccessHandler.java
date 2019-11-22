package com.zy.cn.support;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: application
 * @description: 退出成功处理器
 * @author: 狗蛋
 * @create: 2019-11-03 11:51
 */

public class SecurityLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 前后端无分离跳转到登录页面 ---与下面结合
        //response.sendRedirect("/signOut");
    }

//        @Configuration
//        class WebMvcConfiguration implements WebMvcConfigurer {
//
//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
//                registry.addViewController("/login").setViewName("login");
//                registry.addViewController("/signOut").setViewName("signOut");
//                registry.addViewController("/index").setViewName("index");
//            }
//        }
}