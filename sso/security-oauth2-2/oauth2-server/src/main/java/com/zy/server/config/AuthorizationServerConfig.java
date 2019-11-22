package com.zy.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import javax.sql.DataSource;

/**
 * @program: application
 * @description: 认证服务器
 * @author: 狗蛋
 * @create: 2019-11-02 09:59
 *
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    /***
     * 采用数据库的方式进行存储令牌
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        // 基于 JDBC 实现，令牌保存到数据
        // 要想使用数据库存储，只要提供这些接口的实现类即可，
        // 而框架已经为我们写好 JdbcTokenStore 和 JdbcClientDetailsService
        return new JdbcTokenStore(dataSource);
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



    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 读取客户端配置
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /****
     * AuthorizationEndpoint：用于为授权请求提供服务默认网址：/oauth/authorize
     * TokenEndpoint:用于服务访问令牌的请求
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(true)
                // 该属性就是设置jdbc进行维护code,将cod存储在oauth_code表中
                .authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource))
                // 设置令牌
                .tokenStore(tokenStore())
                // 这段配置加不加都无所谓 默认实现就是DefaultTokenServices
                .tokenServices(tokenServices());
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
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        // 设置access_token的有效时间 默认单位是：秒 设置2个小时
        defaultTokenServices.setAccessTokenValiditySeconds(60*60*2);
        // 也可以设置refresh_token的有效时间默认为：秒 //Refresh_token:12个小时
        defaultTokenServices.setRefreshTokenValiditySeconds(60*60*12);
        // 也可以设置令牌中的附加信息
        // defaultTokenServices.setClientDetailsService();
        return defaultTokenServices;
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


}