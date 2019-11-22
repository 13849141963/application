package com.zy.cn.logout;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: application
 * @description: 退出成功处理器
 * @author: 狗蛋
 * @create: 2019-11-04 15:39
 */
@Component("securityLogoutSuccessHandler")
public class SecurityLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        OAuth2Authentication authentication1 = (OAuth2Authentication) authentication;
//        OAuth2AuthenticationDetails authentication11 = (OAuth2AuthenticationDetails) authentication1.getDetails();
//        String tokenValue =authentication11.getTokenValue();
//
//        Map<String,String> info = new HashMap<>(1);
//        info.put("token",tokenValue);
//
//        Object forObject = auth2RestTemplate.getForObject("http://localhost:8080/tokens/revoke?token="+tokenValue, Object.class);
//        System.out.println(forObject);
        Map<String,Object> result = new HashMap<>();
        // 前后段分离直接响应退出成功即可 如果不是前后端分离项目直接在这里跳转到登陆页面
        result.put("success",true);
        // 用户的信息保存在全局变量中，SecurityContextLogoutHandler的logout方法清空当前用户的信息
        new SecurityContextLogoutHandler().logout(request, null, null);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}