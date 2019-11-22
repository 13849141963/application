package com.zy.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @program: application
 * @description:
 * @author: 狗蛋
 * @create: 2019-10-30 16:33
 */
@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/lllogout")
    public ResponseEntity logout(Authentication authentication){
        OAuth2Authentication auth2Authentication = (OAuth2Authentication)authentication;
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth2Authentication.getDetails();
        String tokenValue = details.getTokenValue();
        ResponseEntity<Object> objectResponseEntity = restTemplate.postForEntity("http://localhost:8080/token", String.class, Object.class  );
        return objectResponseEntity;
    }
}