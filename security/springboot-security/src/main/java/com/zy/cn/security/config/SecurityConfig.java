package com.zy.cn.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityAuthenticationSuccessHandler securityAuthenticationSuccessHandler;

    @Autowired
    private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler;

    /***
     * 密码加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http// 添加验证码校验器 暂未实现 可以通过spring项目通过的 都是一样的
                //.addFilterBefore(imageCodeAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                // 表单登陆
                .formLogin()
                .loginPage("/login")
                // 指定登陆的地址
                .loginProcessingUrl("/userLogin")
                // 自定义成功登陆处理器
                .successHandler(securityAuthenticationSuccessHandler)
                // 自定义失败登陆处理器
                .failureHandler(securityAuthenticationFailureHandler)
                .and()
                // //退出登录相关配置 //自定义退出路径
                .logout().logoutUrl("/logout")
                //  //退出成功后要做的操作（如记录日志），和logoutSuccessUrl互斥
                //.logoutSuccessHandler(new CoreqiLogoutSuccessHandler()) //退出成功后要做的操作（如记录日志），和logoutSuccessUrl互斥
                // .logoutSuccessUrl("/index") //退出成功后跳转的页面
                // .deleteCookies("JSESSIONID")    //退出时要删除的Cookies的名字
                .and()
                // 授权配置
                .authorizeRequests()
                // 指定路径
                .antMatchers("/product/add").hasAnyAuthority("ROLE_ADD_PRODUCT")
                .antMatchers("/product/update").hasAnyAuthority("ROLE_UPDATE_PRODUCT")
                .antMatchers("/product/list").hasAnyAuthority("ROLE_LIST_PRODUCT")
                .antMatchers("/product/delete").hasAnyAuthority("ROLE_DELETE_PRODUCT")
                // 登陆页面不需要认证就可以访问
                .antMatchers("/login").permitAll()
                .antMatchers("/**")
                // 上面路径的资源都需要认证才能访问
                .fullyAuthenticated()
            .and()
            .csrf().disable();
    }

    /***
     * 指定用户名密码权限
     * @param auth
     * @throws Exception
     */
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("zs").password("123").authorities("PRODUCT_ADD","PRODUCT_UPDATE");
    }*/
}