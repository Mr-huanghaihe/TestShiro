package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.redisCache.AddCache;
import com.baizhi.utils.UUIDUtil;
import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class ChapterServiceImpl implements ChapterService{

    @Autowired
    private ChapterDao chapterDao;
    @AddCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> showAll(Integer page, Integer rows, String albumId) {
        List<Chapter> chapters = chapterDao.selectByPage((page - 1) * rows, rows,albumId);
        Integer counts = chapterDao.selectCounts(albumId);
        Integer pages=counts%rows==0?counts/rows:counts/rows+1;
        HashMap<String, Object> map = new HashMap<>();
        map.put("records",counts);
        map.put("total",pages);
        map.put("page",page);
        map.put("rows",chapters);
        return map;
    }

    @Override
    public String save(Chapter chapter) {
        String uuid = UUIDUtil.getUUID();
        chapter.setId(uuid);
        chapter.setUp_date(new Date());
        chapterDao.add(chapter);
        return uuid;
    }

    @Override
    public void remove(String id) {
        chapterDao.delete(id);
    }

    @Override
    public HashMap<String, Object> uploadMusic(MultipartFile url, String id, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();

        String realPath = request.getSession().getServletContext().getRealPath("/bootstrap/music");
        //创建文件夹
        File file = new File(realPath);
        //判断是否存在，如果不存在就创建一个新的文件夹
        if(!file.exists()){
            file.mkdirs();
        }
        //获取源文件名
        String filename = url.getOriginalFilename();
        //重新为源文件拼接新名字
        String name=new Date().getTime()+"-"+filename;
        try {
            //将源文件内容传入上传的新文件中
            url.transferTo(new File(realPath,name));
            //获取文件大小
            long size = url.getSize();
            DecimalFormat format = new DecimalFormat("0.00");
            String s = String.valueOf(size);
            double v = Double.valueOf(s) / 1024 / 1024;
            String newsize=format.format(v)+"MB";
            //获取文件时长
            AudioFileIO fileIO = new AudioFileIO();
            AudioFile audio = fileIO.readFile(new File(realPath, name));
            AudioHeader audioHeader = audio.getAudioHeader();
            int length = audioHeader.getTrackLength();
            String duration=length/60+"分"+length%60+"秒";

            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setUrl(name);
            chapter.setSize(newsize);
            chapter.setDuration(duration);
            chapterDao.update(chapter);

            map.put("success","200");
            map.put("message","上传成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("success","400");
            map.put("message","上传失败");
        }
        return map;
    }

    @Override
    public void downloadMusic(String fileName, HttpServletRequest request, HttpServletResponse response) {
        //获取文件所在路径
        String realPath = request.getSession().getServletContext().getRealPath("/bootstrap/music");
        try {
            //根据路径和名字获取文件
            File file = new File(realPath, fileName);
            //读取文件
            FileInputStream is = new FileInputStream(file);
            //设置文件响应格式   响应头   attachment:以附件的形式下载，   inline:在线打开
            response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"utf-8"));
            ServletOutputStream os = response.getOutputStream();
            //文件下载
            IOUtils.copy(is,os);
            is.close();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Chapter> showByAlbumId(String albumId) {
        List<Chapter> chapters = chapterDao.selectByAlbumId(albumId);
        return chapters;
    }
}
