package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/showAll")
    public HashMap<String, Object> showAll(Integer page, Integer rows, String albumId){
        HashMap<String, Object> map = chapterService.showAll(page, rows, albumId);
        return map;
    }

    @RequestMapping("/edit")
    public String edit(Chapter chapter, String oper, String albumId){
        String uuid=null;
        if("add".equals(oper)){
            chapter.setAlbum_id(albumId);
            uuid = chapterService.save(chapter);
        }
        if("del".equals(oper)){
            chapterService.remove(chapter.getId());
        }
        return uuid;
    }
    @RequestMapping("/upload")
    public HashMap<String ,Object> upload(MultipartFile url, String id, HttpServletRequest request){
        HashMap<String, Object> map = chapterService.uploadMusic(url, id, request);
        return map;
    }
    @RequestMapping("/download")
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response){
        chapterService.downloadMusic(fileName, request, response);
    }
}
