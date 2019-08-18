package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import com.baizhi.feign.FirstFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: untitled
 * @description:
 * @author: Mr.huang
 * @create: 2019-08-16 20:13
 */

@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    private FirstFeignClient firstFeignClient;

    @RequestMapping("/feign")
    public String feign(String name){
        String feign = firstFeignClient.feign(name);
        return "feign 8765 :"+feign;
    }

    @RequestMapping("/user")
    public String feignUser(UserDTO userDTO){
        User user = new User(userDTO.getUsername(),userDTO.getPassword(),userDTO.getAge());
        String feign = firstFeignClient.feigns(user);
        return "feign 8765 :"+feign;
    }

}