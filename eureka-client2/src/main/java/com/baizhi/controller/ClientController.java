package com.baizhi.controller;

import com.netflix.discovery.converters.Auto;
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
@RequestMapping("/book")
public class ClientController {

    @RequestMapping("/book")
    public String book(String name){
        return "client1 8764 is :"+name;
    }
}