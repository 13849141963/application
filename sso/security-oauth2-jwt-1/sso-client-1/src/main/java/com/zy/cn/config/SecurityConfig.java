package com.zy.cn.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @program: application
 * 请注意，我们需要继承 WebSecurityConfigurerAdapter — 如果没有它，
 * 所有路径都将被保护 — 因此用户在尝试访问任何页面时将被重定向到登录页面。
 * 在当前这个示例中，索引页面和登录页面可以在没有身份验证的情况下可以访问。
 * @description:@EnableOAuth2Sso 该注解标注该应用是sso的客户端
 * @description:@EnableGlobalMethodSecurity(prePostEnabled = true) 该注解表示开启security 注解功能
 * security 默认是禁用注解的 想要开启注解就要在继承WebSecurityConfigurerAdapter对的类上加该注解 那么皆可以在controller
 * 的方法上使用@PreAuthorize("hasAuthority('admin')")才会生效
 * @author: 狗蛋
 * @create: 2019-10-30 15:27
 */
@Configuration
@EnableOAuth2Sso // 标注是客户端
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/bootstrap/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .logout()
                .logoutSuccessUrl("http://localhost:8080/logout")
                //.logoutRequestMatcher(new AntPathRequestMatcher("http://localhost:8080/logout"))
                //.deleteCookies("")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // 默认情况下csrf是开启状态的   默认 logout退出的是GET请求的时候CSRF必须是关闭的
                //                              logout如果是不关闭csrf需要GET请求需要下面这样子操作 https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#csrf-logout
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/home")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                //                              logout如果POST请求不关闭csrf需要下面这样子操作
//                <form th:action="@{/logout}" method="post">
//                    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
//                    <input type="submit" value="logout">
//                </form>
                //.csrf().disable()
        .and().cors();
        // 跨域解决方案 https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#cors
        // 跨域解决方案 https://www.cnblogs.com/victorbu/p/11178696.html
    }

}