package com.baizhi.service;


import com.baizhi.entity.Article;

import java.util.HashMap;

public interface ArticleService {

    public HashMap<String,Object> showAll(Integer page, Integer rows);

    public HashMap<String,Object> modify(Article article);

    public void remove(String id);

    public void add(Article article);
}
