package com.gzcr.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: application
 * @description: 客户端相关接口：调用资源服务器相关
 * @author:
 * @create: 2019-11-21
 */
@RestController
public class CRBClientController {

    @Autowired
    private OAuth2RestTemplate auth2RestTemplate;

    /****
     * 资源服务器的地址 his
     */
    @Value("${messages.url:http://localhost:7001}/")
    private String hisMessagesUrl;


    /****
     * 资源服务器的地址 oa
     */
    @Value("${messages.url:http://localhost:7002}/")
    private String oAMessagesUrl;

    /***
     * 调用资源服务器获取对应的资源信息
     * auth2RestTemplate 携带token信息
     * auth2RestTemplate 资深封装了获取token的方法，直接在第三方应用调用即可
     *
     * @return
     */
    @RequestMapping("/requestHis")
    public String requestHis() {
        System.out.println("进入客户端～～～～～～～～～～～～a门户");
        String forObject = auth2RestTemplate.getForObject(hisMessagesUrl + "his", String.class);
        return forObject;
    }

    @RequestMapping("/requestOa")
    public String requestOa() {
        System.out.println("进入客户端～～～～～～～～～～～～a门户");
        String forObject = auth2RestTemplate.getForObject(oAMessagesUrl + "oa", String.class);
        return forObject;
    }

}