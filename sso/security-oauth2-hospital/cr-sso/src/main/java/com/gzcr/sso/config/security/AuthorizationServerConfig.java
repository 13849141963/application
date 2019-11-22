package com.gzcr.sso.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: application
 * @description: 认证服务器相关配置
 * @author:
 * AuthorizationServerConfig 来配置授权服务机制，并继承 AuthorizationServerConfigurerAdapter
 * 该类重写 configure 方法定义授权服务器策略
 * /oauth/authorize：授权端点
 * /oauth/token：令牌端点
 * /oauth/confirm_access：用户确认授权提交端点
 * /oauth/error：授权服务错误信息端点
 * /oauth/check_token：用于资源服务访问的令牌解析端点
 * /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /***
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /***
     * 存储token的相关附加信息
     */
    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    /****
     * token存储的容器
     */
    @Autowired
    private TokenStore jwtTokenStore;

    /***
     * token的生成方式和加密的类
     */
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /***
     * UserDetailService
     */
    @Autowired
    private UserDetailsService userDetailsService;


    /***
     * 密码加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /***
     * 配置客户端的相关配置项:通过oauth2提供的相关sql,将客户端相信息配置到数据库中
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /****
         * clientId：客户端标识 ID
         * secret：客户端安全码
         * scope：客户端访问范围，默认为空则拥有全部范围
         * authorizedGrantTypes：客户端使用的授权类型，默认为空
         * authorities：客户端可使用的权限
         */
        clients.withClientDetails(jdbcClientDetailsService());
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 访问认证服务器的tokenKey是需要进行认证 [默认值时拒绝访问，未来客户端进行访问进行如下配置]
        security
                // 该属性表示开启过滤认证客户端信息，进行token令牌操作前置
                .allowFormAuthenticationForClients()
                // 验证端口进行认认证权限访问获取令牌
                .tokenKeyAccess("isAuthenticated()")
                // 验证端口进行认认证权限校验令牌
                .checkTokenAccess("isAuthenticated()");
    }

    /***
     * 断点相关配置 /oauth/token 入口 配置入口点
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 该属性就是设置jdbc进行维护code,将cod存储在oauth_code表中
                .authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource))
                // 引导设置tokenStore设置token存储方式
                .tokenStore(jwtTokenStore)
                .reuseRefreshTokens(true)
                .userDetailsService(userDetailsService)
                // 加密token类
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenServices(tokenServices());

        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null){
            // 添加token中携带的附加信息相关类
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);

            endpoints.tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /****
     * 处理所有令牌的类是DefaultTokenServices –在我们的配置中定义为bean：
     * 下面这段配置可以直接写在configure(AuthorizationServerEndpointsConfigurer endpoints)中
     * @return
     */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        // 设置access_token的有效时间 默认单位是：秒 设置2个小时
        defaultTokenServices.setAccessTokenValiditySeconds(60*60*2);
        // 也可以设置refresh_token的有效时间默认为：秒 //Refresh_token:12个小时
        defaultTokenServices.setRefreshTokenValiditySeconds(60*60*12);
        // 也可以设置令牌中的附加信息
        // defaultTokenServices.setClientDetailsService();
        return defaultTokenServices;
    }

    /***
     * 将客户端的信息存储到数据库
     * @return
     */
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService(){
        // 基于 JDBC 实现，需要事先在数据库配置客户端信息
        return new JdbcClientDetailsService(dataSource);
    }

}