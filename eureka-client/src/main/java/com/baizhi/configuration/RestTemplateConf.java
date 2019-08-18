package com.baizhi.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: untitled
 * @description: 将restTemplate交由spring工厂创建
 * @author: Mr.huang
 * @create: 2019-08-15 20:17
 */
@Configuration
public class RestTemplateConf {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}