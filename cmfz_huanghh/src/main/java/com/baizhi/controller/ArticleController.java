package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @program: untitled
 * @description: ArticleController
 * @author: Mr.huang
 * @create: 2019-08-01 20:51
 */

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @RequestMapping("/showAll")
    public HashMap<String,Object> showAll(Integer page, Integer rows){
        HashMap<String, Object> map = articleService.showAll(page, rows);
        return map;
    }
    @RequestMapping("/edit")
    public void edit(String oper,String id){
        if("del".equals(oper)){
            articleService.remove(id);
        }
    }
    @RequestMapping("/status")
    public HashMap<String,Object> status(String id,String status){
        Article article = new Article();
        article.setId(id);
        article.setStatus(status);
        HashMap<String, Object> map = articleService.modify(article);
        return map;
    }
    @RequestMapping("/update")
    public HashMap<String,Object> update(HttpServletRequest request,MultipartFile picture,Article article,String id){
        /*HashMap<String, Object> map=null;
        article.setId(id);
        if(article.getPicture().isEmpty()){
            map= articleService.modify(article);
        }else{
            String realPath = request.getSession().getServletContext().getRealPath("/upload/picture");
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }
            String filename = picture.getOriginalFilename();
            String name=new Date().getTime()+"-"+filename;
            try {
                picture.transferTo(new File(realPath,name));
            }catch (Exception e){
                e.printStackTrace();
            }
            article.setPicture(name);
            articleService.modify(article);
        }*/
        HashMap<String, Object> map = articleService.modify(article);
        return map;
    }

    @RequestMapping("/upload")
    public HashMap<String, Object> upload(MultipartFile photo,HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/picture");
        File file = new File(realPath);
        if(!file.exists()) file.mkdirs();
        String filename = photo.getOriginalFilename();
        String name=new Date().getTime()+"-"+filename;

        //获取  http
        String scheme = request.getScheme();
        //获取Ip   localhost
        String serverName = request.getServerName();
        //获取端口号  8989
        int serverPort = request.getServerPort();
        //获取项目名 cmfz
        String contextPath = request.getContextPath();
        //网络路径的拼接
        String serverPath=scheme+"://"+serverName+":"+serverName+":"+serverPort+":"+contextPath+"/upload/picture/"+name;
        try {
            photo.transferTo(new File(realPath,name));
            map.put("error",0);
            map.put("url",serverPath);
        }catch (Exception e){
            e.printStackTrace();
            map.put("error","上传成功！");
            map.put("url","上传失败！");
        }
        return map;
    }

    @RequestMapping("/preview")
    public HashMap<String,Object> preview(HttpServletRequest request){
        HashMap<String, Object> maps = new HashMap<>();
        ArrayList<Object> lists = new ArrayList<>();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/picture");
        //获取文件夹
        File file = new File(realPath);
        //获取文件夹下所有的文件名称
        String[] names = file.list();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            HashMap<String, Object> map = new HashMap<>();
            map.put("is_dir",false);//是否是文件夹
            map.put("has_file",false);//是否是文件
            File file1 = new File(realPath, name);
            map.put("filesize",file1.length());//文件的大小
            map.put("is_photot",true);//是否是图片
            String extension = FilenameUtils.getExtension(name);
            map.put("filetype",extension);//图片的格式,即后缀
            map.put("filename",name);//文件的名字
            String[] split = name.split("-");
            String time=split[0];
            long times = Long.parseLong(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            String format = dateFormat.format(times);
            map.put("datetime",dateFormat);
            //封装进集合
            lists.add(map);
        }
        //网络路径
        maps.put("current_url","http://localhost:8989/cmfz/upload/picture/");
        //文件数量
        maps.put("total_count",lists.size());
        //文件集合
        maps.put("file_list",lists);
        return maps;
    }


    @RequestMapping("/add")
    public void add(Article article,MultipartFile picture,HttpServletRequest request){
        /*String realpath=request.getSession().getServletContext().getRealPath("/upload/picture");
        File file = new File(realpath);
        if(!file.exists()){
            file.mkdirs();
        }
        String filename = picture.getOriginalFilename();
        System.out.println(filename);
        String name=new Date().getTime()+"-"+filename;
        try {
            picture.transferTo(new File(realpath,name));
        }catch (Exception e){
            e.printStackTrace();
        }
        article.setPicture(name);
        System.out.println(article);*/
        articleService.add(article);
    }

    @RequestMapping("/delete")
    public void delete(String id){
        articleService.remove(id);
    }
}