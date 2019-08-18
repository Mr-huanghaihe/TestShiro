package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.redisCache.AddCache;
import com.baizhi.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;
    @AddCache
    @Override
    public HashMap<String, Object> showAll(Integer page, Integer rows) {
        Integer start=(page-1)*rows;
        List<Album> albums = albumDao.selectAll(start, rows);
        HashMap<String, Object> map = new HashMap<>();
        Integer counts=albumDao.selectCounts();
        Integer total=counts%rows==0?counts/rows:counts/rows+1;
        map.put("records",counts);
        map.put("total",total);
        map.put("page",page);
        map.put("rows",albums);
        return map;
    }

    @Override
    public String save(Album album) {
        String uuid = UUIDUtil.getUUID();
        album.setId(uuid);
        album.setPub_date(new Date());
        albumDao.insert(album);
        return uuid;
    }

    @Override
    public String modify(Album album) {
        albumDao.update(album);
        return album.getId();
    }

    @Override
    public void remove(String id) {
        albumDao.delete(id);
    }

    @Override
    public Album showOneAlbum(String id) {
        Album album = albumDao.selectOneAlbum(id);
        return album;
    }
}
