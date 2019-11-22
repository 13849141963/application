package com.zy.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}