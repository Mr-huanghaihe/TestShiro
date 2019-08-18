package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public interface ChapterService {

    public HashMap<String,Object> showAll(Integer page,Integer rows,String albumId);

    public String save(Chapter chapter);

    public void remove(String id);

    public HashMap<String,Object> uploadMusic(MultipartFile url, String id, HttpServletRequest request);

    public void downloadMusic(String fileName, HttpServletRequest request, HttpServletResponse response);

    public List<Chapter> showByAlbumId(String albumId);
}
