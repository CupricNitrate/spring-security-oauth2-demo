package com.cupricnitrate.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 硝酸铜
 * @date 2021/9/27
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ResourceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }
}
