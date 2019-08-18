package com.baizhi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @program: untitled
 * @description:
 * @author: Mr.huang
 * @create: 2019-08-15 20:19
 */
@RestController
@RequestMapping("/test")
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test")
    public String test(String names){
        //调用其他微服务实例   http请求   httpClint
        String forObject = restTemplate.getForObject("http://EUREKA-CLIENT1/book/book?name="+names, String.class);
        return forObject;
    }
}