package com.cupricnitrate.authority;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 硝酸铜
 * @date 2021/9/23
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.cupricnitrate.authority.mapper")
public class AuthorityServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorityServerApplication.class, args);
    }
}
