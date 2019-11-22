package com.zy.cn.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * 图片验证码拦截器
 */
public class ImageCodeAuthenticationFilter extends OncePerRequestFilter {

    private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().contains("/login")){
            // 校验验证码
            try {
                String imageCode = request.getParameter("imageCode");
                String sessionCode = (String) request.getSession().getAttribute("imageCode");
                if (StringUtils.isEmpty(imageCode)){
                    throw new ImageCodeException("验证码不能为空～～～");
                }
                if (!imageCode.trim().equalsIgnoreCase(sessionCode.trim())){
                    throw new ImageCodeException("验证码不一致～～～");
                }
            } catch (AuthenticationException e) {
                securityAuthenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    public SecurityAuthenticationFailureHandler getSecurityAuthenticationFailureHandler() {
        return securityAuthenticationFailureHandler;
    }

    public void setSecurityAuthenticationFailureHandler(SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler) {
        this.securityAuthenticationFailureHandler = securityAuthenticationFailureHandler;
    }
}