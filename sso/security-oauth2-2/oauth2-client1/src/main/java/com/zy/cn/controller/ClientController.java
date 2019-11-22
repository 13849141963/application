package com.zy.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: application
 * @description: 客户端相关接口：调用资源服务器相关
 * @author: 狗蛋
 * @create: 2019-11-04 09:28
 */
@RestController
public class ClientController {

    @Autowired
    private OAuth2RestTemplate auth2RestTemplate;

    /****
     * 资源服务器的地址
     */
    @Value("${messages.url:http://localhost:8081}/contents")
    private String messagesUrl;


    /***
     * 调用资源服务器获取对应的资源信息
     * auth2RestTemplate 携带token信息
     * auth2RestTemplate 资深封装了获取token的方法，直接在第三方应用调用即可
     *
     * @return
     */
    @RequestMapping("/user")
    public String selectByPrimaryKey() {
        String forObject = auth2RestTemplate.getForObject(messagesUrl + "/admin", String.class);
        return forObject;
    }

    /****
     * 暂时没用到
     * @param authentication
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/me")
    public boolean user(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        OAuth2Authentication authentication1 = (OAuth2Authentication) authentication;
        OAuth2AuthenticationDetails authentication11 = (OAuth2AuthenticationDetails) authentication1.getDetails();
        String tokenValue =authentication11.getTokenValue();

        Map<String,String> info = new HashMap<>(1);
        info.put("token",tokenValue);


        String forObject = auth2RestTemplate.getForObject("http://localhost:8080/tokens/revoke?token="+tokenValue, String.class);
        System.out.println(forObject);

        SecurityContextHolder.clearContext();
        new SecurityContextLogoutHandler().logout(request,null,null);


        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("JSESSIONID")){
                    cookie.setMaxAge(0);
                    cookie.setValue(null);
                    cookie.setPath("/");
                    System.out.println("被删除的cookie名字为:"+cookie.getName());
                    response.addCookie(cookie);
                }
            }
        }

        return true;
    }

}