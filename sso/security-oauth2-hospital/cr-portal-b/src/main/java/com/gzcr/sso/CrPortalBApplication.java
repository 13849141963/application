package com.gzcr.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

@SpringBootApplication
public class CrPortalBApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrPortalBApplication.class, args);
    }

    /****
     * OAuth2RestTemplate 该对象封装来获取token的方法
     * 通过OAuth2RestTemplate调用资源服务器获取资源信息
     * @param auth2ClientContext
     * @param details
     * @return
     */
    @Bean
    public OAuth2RestTemplate restTemplate(OAuth2ClientContext auth2ClientContext,
                                           OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details,auth2ClientContext);
    }
}
