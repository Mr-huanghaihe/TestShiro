package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.redisCache.AddCache;

import java.util.HashMap;

public interface AlbumService {
    public HashMap<String,Object> showAll(Integer page,Integer rows);
    public String save(Album album);
    public String modify(Album album);
    public void remove(String id);
    public Album showOneAlbum(String id);
}
