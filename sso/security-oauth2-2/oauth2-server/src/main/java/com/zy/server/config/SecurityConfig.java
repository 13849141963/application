package com.zy.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * @program: application
 * @description:
 * @author: 狗蛋
 * @create: 2019-11-02 10:04
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /***
     * 自定义的userDetailService
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /***
     * 密码加密处理器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /***
     * 告诉security 使用自定义的userDetailService
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    /***
     * 放行静态资源 在这里放行比在security配置中放行比较好一点 因为先加载springMvc的相关配置
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        // 将 check_token 暴露出去，否则资源服务器访问时报 403 错误   放行登陆页面所需要的就是文件
        web.ignoring().antMatchers("/oauth/check_token","/exit","/assets/**", "/css/**", "/images/**");
    }

    /***
     * 拓展一些相关的配置项
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    // 指定登陆页面的路径，并不需要认证就可以访问
                .formLogin().loginPage("/login").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}