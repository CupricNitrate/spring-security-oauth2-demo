package com.cupricnitrate.resource.config;

import feign.Feign;
import feign.Retryer;
import feign.querymap.BeanQueryMapEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 硝酸铜
 * @date 2021/9/23
 */
@Configuration
public class FeignConfig{


    /**
     * 替换解析queryMap的类，实现父类中变量的映射
     * Bean的name一定要全局唯一
     *
     * @return
     */
    @Bean(name = "resource-server-feignBuilder")
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .queryMapEncoder(new BeanQueryMapEncoder())
                .retryer(Retryer.NEVER_RETRY);
    }
}
