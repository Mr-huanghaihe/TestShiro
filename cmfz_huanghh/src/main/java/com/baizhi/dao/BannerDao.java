package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    public List<Banner> selectAll(@Param("start") Integer start,@Param("rows")Integer rows);
    public void insertOneBanner(Banner banner);
    public void deleteOneBanner(String id);
    public void updateBanner(Banner banner);
    public void updateStatus(Banner banner);
    public Banner selectById(String id);
    public Integer selectCounts();
}
