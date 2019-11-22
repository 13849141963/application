package com.gzcr.sso.resources.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @program: application
 * @description:
 * @author:
 * @create: 2019-11-21
 */
@Configuration
// 该注解标注该应用是一个资源服务器
@EnableResourceServer
// 加上该注解标注启用注解权限认证 ：默认springSecurity禁用注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /****
     * 资源服务器的相关配置
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 匹配的资源路径需要哪些权限可以访问
                .antMatchers("/").hasAuthority("ROLE_ADMIN")
                .antMatchers("/his").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user").hasAuthority("ROLE_ADMIN");
    }
}