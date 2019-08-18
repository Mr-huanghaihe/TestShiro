package com.baizhi.controller;

import com.baizhi.entity.ChinaMap;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.utils.PhoneMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @program: untitled
 * @description: Controller of user
 * @author: Mr.huang
 * @create: 2019-08-01 18:59
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/phoneCode")
    public void sendPhoneCode(String phoneNumbers){
        String randomCode = PhoneMessageUtil.getRandomCode(6);
        //将验证码存入session,后面做校验
        System.out.println("验证码为："+randomCode);
        String phoneMsg = PhoneMessageUtil.getPhoneMsg(phoneNumbers, randomCode);
        System.out.println(phoneMsg);
    }

    @RequestMapping("/showAll")
    public HashMap<String,Object> showAll(Integer page,Integer rows){
        HashMap<String, Object> map = userService.showAll(page, rows);
        return map;
    }
    @RequestMapping("/status")
    public HashMap<String,Object> status(String id,String status){
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        HashMap<String, Object> map = userService.modify(user);
        return map;
    }
    @RequestMapping("/export")
    public void export(){
        userService.exportAll();
    }
    @RequestMapping("/import")
    public void importUsers(){
        userService.importUsers();
    }
    @RequestMapping("/statistics")
    public HashMap<String, Object> showUsers(){
        HashMap<String, Object> map = userService.showUsers();
        return map;
    }

    @RequestMapping("/echartsMap")
    public ArrayList<ChinaMap> showChinaMap(){
        ArrayList<ChinaMap> chinaMaps = userService.showChinaMap();
        return chinaMaps;
    }
    @RequestMapping("/goEasy")
    public void goEasy(){
        userService.showGoEasy();
    }
    @RequestMapping("/goEasyAdd")//实时推送注册消息
    public void goEasyAdd(User user){
        userService.add(user);
    }
}