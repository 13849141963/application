package com.gzcr.sso.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

/**
 * @program: application
 * @description: 客户端的相关配置类
 * @author:
 * @create: 2019-11-21
 */
@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
public class ClientSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LogoutSuccessHandler securityLogoutSuccessHandler;

    @Value("${server.servlet.session.cookie.name}")
    private String cookieName;

    @Value("${SSO.LOGOUT}")
    private String logoutUrl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                // 开启csrf功能
                .csrf()
                // A.从Spring Security 4.0开始，默认情况下会启用CSRF保护，以防止CSRF攻击应用程序，Spring Security CSRF会针对PATCH，POST，PUT和DELETE方法进行防护。
                // 我这边是spring boot项目，在启用了@EnableWebSecurity注解后，csrf保护就自动生效了
                // 所以在默认配置下，即便已经登录了，页面中发起PATCH，POST，PUT和DELETE请求依然会被拒绝，并返回403，需要在请求接口的时候加入csrfToken才行
                // 开启csrf功能 默认对POST类型的接口进行拦截 需要在页面中添加
                // freemarker 针对csrf的解决方案 ===》针对表单提交，可以在表单中增加如下隐藏域<input  type = “hidden”  name = “${_csrf.parameterName}”  value = “${_csrf.token}” /> 解决第三方跨站请求访问
                // 如果您使用的是JSON，则无法在HTTP参数中提交CSRF令牌。相反，您可以在HTTP头中提交令牌。一个典型的模式是将CSRF令牌包含在元标记中。下面显示了一个JSP示例
                // <meta  name = “_csrf” content = “${_csrf.token}” />
                //	<!-- 默认标题名称是X-CSRF-TOKEN  -->
                //	<meta  name = “_csrf_header”  content = “${_csrf.headerName}” />
                // 您可以将令牌包含在所有Ajax请求中。如果您使用jQuery，可以使用以下方法完成此操作:
//                var token = $("meta[name='_csrf']").attr("content");
//                var header = $("meta[name='_csrf_header']").attr("content");
//                $.ajax({
//                        url:url,
//                        type:'POST',
//                        async:false,
//                        dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
//                        beforeSend: function(xhr) {
//                            xhr.setRequestHeader(header, token);  //发送请求前将csrfToken设置到请求头中
//                        },
//                        success:function(data,textStatus,jqXHR){
//                        }
//                })
                // 获取csrftoken两种方式 HttpSessionCsrfTokenRepository  将token信息保存的session中，通过模版或者表达式获取，默认采用这种方式
                // <input  type = “hidden”  name = “${_csrf.parameterName}”  value = “${_csrf.token}” />
                // 采用cooke方式存储，设置httpOnly 为false 这样可以通过jquery获取设置参数
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                // B. X-Frame-Options 是否允许网页被iFrame
                // DENY 不允许网页被iframe
                //SAMEORIGIN 允许网页被同一域名iframe
                //ALLOW-FROM 允许任意网页iframe该网页
                .headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.DENY))
                .and()
                // C.security 退出功能在csrf开启的时候只能使用post进行注销用户 ，不建议使用get提交注销用户
                // 强制使用GET注销用户的方法如下 https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#csrf-logout
                // 在这里采用了get注销用户就是为了演示 如果用post注销就在页面写一个注销的form表单 请求方式为post
                .logout()
                // 删除cookie的名字
                .deleteCookies(cookieName)
                .logoutSuccessUrl(logoutUrl)
                //.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                // logout 退出的两种方式 https://segmentfault.com/a/1190000013531863?utm_source=tag-newest
                //.logoutSuccessHandler(securityLogoutSuccessHandler);
                .and()
                //所有请求都进行认证才可以访问
                .authorizeRequests()
                .anyRequest().authenticated();
    }


    @Override
    public void configure(WebSecurity web) {
        // 放行静态页面
        web.ignoring().antMatchers("/bootstrap/**");
    }
}