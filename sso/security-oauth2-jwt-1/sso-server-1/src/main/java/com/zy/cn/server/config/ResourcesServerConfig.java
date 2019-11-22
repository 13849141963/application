package com.zy.cn.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @program: application
 * @description: 资源服务器
 * @author: 狗蛋
 * @create: 2019-11-03 11:06
 */
@Configuration
@EnableResourceServer
public class ResourcesServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 资源服务器匹配的相关资源路径信息
        http.requestMatchers().antMatchers("/api/**")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}