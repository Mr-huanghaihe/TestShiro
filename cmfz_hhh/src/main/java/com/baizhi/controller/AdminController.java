package com.baizhi.controller;


import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.utils.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    @ResponseBody
    public HashMap<String, Object> login(Admin admin, HttpSession session, String code) {
        HashMap<String, Object> map = adminService.queryOne(admin, code, session);
        return map;
    }

    @RequestMapping("/code")
    public String getCode(HttpServletResponse response, HttpSession session) {
        String code = ImageCodeUtil.getSecurityCode();
        session.setAttribute("code", code);
        BufferedImage image = ImageCodeUtil.createImage(code);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/exit")
    public ModelAndView exit(HttpSession session,String message){
        session.invalidate();
        message="退出成功！";
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message",message);
        modelAndView.setViewName("login/login");
        return modelAndView;
    }
}
