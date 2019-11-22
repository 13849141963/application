package com.zy.cn.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @program: application
 * @description:@EnableOAuth2Sso 该注解标注该应用是sso的客户端
 * @description:@EnableGlobalMethodSecurity(prePostEnabled = true) 该注解表示开启security 注解功能
 * security 默认是禁用注解的 想要开启注解就要在继承WebSecurityConfigurerAdapter对的类上加该注解 那么皆可以在controller
 * 的方法上使用@PreAuthorize("hasAuthority('admin')")才会生效
 * @author: 狗蛋
 * @create: 2019-10-30 15:27
 */
@Configuration
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().logoutSuccessUrl("http://localhost:8080/logout");
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable().cors();
    }
}