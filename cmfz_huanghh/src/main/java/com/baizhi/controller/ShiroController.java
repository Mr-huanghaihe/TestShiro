package com.baizhi.controller;

import com.baizhi.entity.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: untitled
 * @description: 使用SHIRO认证授权
 * @author: Mr.huang
 * @create: 2019-08-13 17:30
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {

    @RequestMapping("/login")
    public String login(Admin admin){
        //获得主体
        Subject subject = SecurityUtils.getSubject();
        //将身份信息和凭证信息放入令牌
        UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
        //执行认证
        try {
            subject.login(token);
            return "redirect:/main/main.jsp";
        }catch (UnknownAccountException e){
            //用户名错误
            return "redirect:/login/login.jsp";
        }catch (IncorrectCredentialsException e){
            //密码错误
            return "redirect:/login/login.jsp";
        }
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login/login.jsp";
    }
}