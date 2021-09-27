package com.cupricnitrate.authority.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


/**
 * @author 硝酸铜
 * @date 2021/8/30
 */

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //授权模式登陆页面,这里采用默认的登陆页面
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                // 授权模式这里采用默认的页面，登陆成功后从session中拿取回调url
                //.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(req -> req
                        //oauth2 的接口在 /oauth 接口下面，这里配置其不走SpringSecurity的认证流程
                        .antMatchers("/oauth/**").permitAll()
                        .anyRequest().authenticated()
                ) ;
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/error",
                        "/resources/**",
                        "/static/**",
                        "/public/**",
                        "/h2-console/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/v2/api-docs/**",
                        "/doc.html",
                        "/swagger-resources/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
