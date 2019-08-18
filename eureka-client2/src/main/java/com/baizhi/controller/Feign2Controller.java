package com.baizhi.controller;

import com.baizhi.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: untitled
 * @description:
 * @author: Mr.huang
 * @create: 2019-08-16 20:17
 */

@RestController
@RequestMapping("/client")
public class Feign2Controller {

    @RequestMapping("/client")
    public String feign(String name){
        return "feign 8764 :"+name;
    }

    @RequestMapping("/user")
    public String feign(@RequestBody User user){
        return "feign 8764 :"+user;
    }
}

