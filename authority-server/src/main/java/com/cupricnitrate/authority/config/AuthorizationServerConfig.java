package com.cupricnitrate.authority.config;

import com.cupricnitrate.authority.exception.translator.AuthWebResponseExceptionTranslator;
import com.cupricnitrate.authority.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.Collections;

/**
 * `@EnableAuthorizationServer` 注解表示激活授权服务器
 * @author 硝酸铜
 * @date 2021/8/27
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 密钥对
     */
    @Resource
    private KeyPair keyPair;

    /**
     * 数据源
     * RedisTokenStore 需要RedisConnectionFactory，其会读取配置文件spring.redis的配置
     */
    @Resource
    private RedisConnectionFactory connectionFactory;

    /**
     * 数据源
     * 读取配置文件中spring.datasource的配置
     */
    @Resource
    private DataSource dataSource;

    /**
     * 用户service
     */
    @Resource
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * 密码编码器
     */
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthenticationManager authenticationManager;


    /**
     * 配置授权服务器的 token 接入点
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 与SpringSecurity中的access()方法类似,设置复杂安全表达式
                // 设置可以请求接入点的安全表达式为`permitAll()`
                .tokenKeyAccess("permitAll()")
                // 设置检查token的安全表达式为`isAuthenticated()`，已认证
                .checkTokenAccess("isAuthenticated()")
                // 允许进行表单认证
                .allowFormAuthenticationForClients()
                // 设置oauth_client_details中的密码编码器
                .passwordEncoder(passwordEncoder);
    }

    /**
     * 配置 Jdbc 版本的 JdbcClientDetailsService
     * 也就是读取oauth_client_details表的信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 读取配置文件中spring.datasource的配置
        clients.jdbc(dataSource);
    }

    /**
     * 配置授权访问的接入点
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // endpoints.pathMapping("/oauth/token","/token/login");  设置token生成请求地址
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Collections.singletonList(accessTokenConverter()));

        endpoints
                // chain和accessTokenConverter()二选一即可
                //.accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(chain)
                // token 持久化
                .tokenStore(tokenStore())
                // 配置认证 manager
                .authenticationManager(authenticationManager)
                // 配置用户
                .userDetailsService(userDetailsServiceImpl)
                // 自定义异常翻译器
                .exceptionTranslator(new AuthWebResponseExceptionTranslator());
    }

    /**
     * 使用jwt生成token
     * 如果需要自定义token或者获取token接口的返回体，需要实现TokenEnhancer接口的enhance方法，具体可以看一下JwtAccessTokenConverter类
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //非对称加密签名
        converter.setKeyPair(this.keyPair);

        //对称加密签名
        //converter.setSigningKey("xxx");
        return converter;
    }

    /**
     * token持久化
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        //将token保存在redis中
        return new RedisTokenStore(connectionFactory);

        //将token保存在内存中
        //return new InMemoryTokenStore();


        //不用管如何进行存储（内存或磁盘），因为它可以把相关信息数据编码存放在令牌里。JwtTokenStore 不会保存任何数据
        //return new JwtTokenStore(accessTokenConverter());

        //将token保存在数据库中
        //return new JdbcTokenStore(dataSource);
    }

}
