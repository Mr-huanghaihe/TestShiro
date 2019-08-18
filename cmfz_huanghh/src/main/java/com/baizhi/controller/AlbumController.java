package com.baizhi.controller;


import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;
    @RequestMapping("/showAll")
    public HashMap<String,Object> showAll(Integer page,Integer rows)throws Exception{
        HashMap<String, Object> map = albumService.showAll(page, rows);
        return map;
    }
    @RequestMapping("/edit")
    public String edit(Album album,String oper)throws Exception{
        String uuid=null;
        if(oper.equals("add")){
            uuid = albumService.save(album);
        }else if(oper.equals("edit")){
            if(album.getCover()==""){
                album.setCover(null);
            }
            uuid=albumService.modify(album);
        }else if(oper.equals("del")){
            albumService.remove(album.getId());
        }
        return uuid;
    }

    @RequestMapping("/upload")
    public void upload(MultipartFile cover, HttpServletRequest request,String id)throws Exception{
        if(cover.isEmpty()){

        }else{
            String realPath = request.getSession().getServletContext().getRealPath("/bootstrap/img");
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }
            String filename = cover.getOriginalFilename();
            String name=new Date().getTime()+"-"+filename;
            try {
                cover.transferTo(new File(realPath,name));
            }catch (Exception e){
                e.printStackTrace();
            }
            Album album = new Album();
            album.setId(id);
            album.setCover(name);
            albumService.modify(album);
        }
    }
}
