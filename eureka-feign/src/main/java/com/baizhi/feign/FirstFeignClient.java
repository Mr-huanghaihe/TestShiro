package com.baizhi.feign;

import com.baizhi.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="EUREKA-CLIENT1")
public interface FirstFeignClient {

    //真实的访问的服务的名字
    @RequestMapping("/client/client")
    public String feign(@RequestParam("name") String name);

    @RequestMapping(value = "/client/user", method = RequestMethod.POST)
    String feigns(User user);
}
