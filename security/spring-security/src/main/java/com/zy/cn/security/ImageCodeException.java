package com.zy.cn.security;

import org.springframework.security.core.AuthenticationException;

/***
 * 自定义验证码异常处理器
 */
public class ImageCodeException extends AuthenticationException {
    public ImageCodeException(String msg) {
        super(msg);
    }
}