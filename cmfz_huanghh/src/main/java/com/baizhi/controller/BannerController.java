package com.baizhi.controller;


import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping("/showAll")
    @ResponseBody
    public HashMap<String, Object> showAll(Integer page, Integer rows) {
        HashMap<String, Object> map = bannerService.showAll(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody  //如果不写该注解，则返回值为String，return 一个字符串或跳转页面
    public String edit(Banner banner, String oper) throws Exception {
        String uid = null;
        if ("add".equals(oper)) {
            uid = bannerService.addBanner(banner);
            System.out.println(banner);
        } else if ("edit".equals(oper)) {
            if(banner.getImg_path()==""){
                banner.setImg_path(null);
            }
            uid = bannerService.modifyBanner(banner);
        } else if ("del".equals(oper)) {
            bannerService.removeBanner(banner.getId());
        }
        return uid;
    }

    @RequestMapping("/upload")
    public void upload(MultipartFile img_path, String id, HttpServletRequest request) {
        if (img_path.isEmpty()) {

        } else {
            String realPath = request.getSession().getServletContext().getRealPath("/bootstrap/img");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String filename = img_path.getOriginalFilename();
            String name = new Date().getTime() + "-" + filename;
            try {
                img_path.transferTo(new File(realPath, name));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Banner banner = new Banner();
            banner.setId(id);
            banner.setImg_path(name);
            //做修改
            bannerService.modifyBanner(banner);
        }
    }

    @RequestMapping("/status")
    @ResponseBody
    public HashMap<String, Object> status(String id, String status) throws Exception {
        HashMap<String, Object> map = bannerService.modifyStatus(id, status);
        return map;
    }
}
