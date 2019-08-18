package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.entity.User;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.ChapterService;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: untitled
 * @description: 服务端接口
 * @author: Mr.huang
 * @create: 2019-08-08 08:26
 */

@RestController
public class InterfaceController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private UserService userService;

    @RequestMapping("wen")
    public HashMap<String,Object> wen(String albumId){
        HashMap<String, Object> maps = new HashMap<>();
        Album album = albumService.showOneAlbum(albumId);
        List<Chapter> chapters = chapterService.showByAlbumId(albumId);
        maps.put("album",album);
        maps.put("chapters",chapters);
        return maps;
    }
    @RequestMapping("modify")
    public User modify(String id, String name, String ahama, String phone, String password, String salt, String photo, String sex, String city, String sign, String status, Date reg_date,String guruId){
        User user = userService.showOneUser(id);
        name="测试服务端接口";
        user.setName(name);
        return user;
    }
}