package com.gzcr.sso.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @program: application
 * @description: jwt相关配置
 * @author:
 * @create: 2019-11-21
 */
@Configuration
public class JwtTokenConfig {

    /***
     *设置jwt token的存储方式 参数是：JwtAccessTokenConverter
     * 注意 ：这里可以采用多种存储token的方式 如下注释
     * @return
     */
    @Bean
    public TokenStore jwtStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /***
     * 可以采用redis进行存储token 在这里注释需要引入redis的相关的maven坐标
     * @return
     */
//    @Bean
//    public TokenStore jwtStore(){
//        return new RedisTokenStore(RedisConnectionFactory);
//    }

    /***
     * token处理方式：进行token生成和token密签名 加密
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 设置签名秘钥 加密需要用到这个秘钥 验证签名的时候也需要这个秘钥进行验签
        converter.setSigningKey("sso");
        return converter;
    }

    /***
     * 通过这个来设置token中的其他相关附加信息
     *  注释：加@Primary注解的意思就是让spring知道我们在使用这个相关配置类
     * @return
     */
    @Bean
    @Primary
    public TokenEnhancer tokenEnhancer(){
        return new JwtTokenEnhancerConfig();
    }


}