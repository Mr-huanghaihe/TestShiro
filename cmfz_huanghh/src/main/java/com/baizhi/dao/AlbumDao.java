package com.baizhi.dao;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    public List<Album> selectAll(@Param("start") Integer start,@Param("rows") Integer rows);
    public Integer selectCounts();
    public void insert(Album album);
    public void update(Album album);
    public void delete(String id);
    public Album selectOneAlbum(String id);
}
