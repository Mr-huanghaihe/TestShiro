package com.baizhi.service;


import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.redisCache.AddCache;
import com.baizhi.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: untitled
 * @description: About user and guru
 * @author: Mr.huang
 * @create: 2019-08-01 18:44
 */

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @AddCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public HashMap<String, Object> showAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        List<Article> articles = articleDao.selectByPage((page - 1) * rows, rows);
        Integer counts = articleDao.selectCounts();
        Integer total = counts % rows == 0 ? counts / rows : counts / rows + 1;
        map.put("records", counts);
        map.put("total", total);
        map.put("page", page);
        map.put("rows", articles);
        return map;
    }

    @Override
    public HashMap<String,Object> modify(Article article) {

        HashMap<String, Object> map=new HashMap<String, Object>();
        try {
            articleDao.update(article);
            map.put("success","200");
            map.put("message","修改成功");
        }catch (Exception e){
            e.getStackTrace();
            map.put("success","400");
            map.put("message","修改失败");
        }
        return map;
    }

    @Override
    public void remove(String id) {
        articleDao.delete(id);
    }

    @Override
    public void add(Article article) {
        String uuid = UUIDUtil.getUUID();
        article.setId(uuid);
        article.setStatus("正常");
        article.setUp_date(new Date());
        article.setGuruId("1");
        System.out.println(article);
        articleDao.add(article);
    }
}