package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.HashMap;
import java.util.List;

public interface BannerService {

    public HashMap<String,Object> showAll(Integer pageNum, Integer rows);
    public String modifyBanner(Banner banner);
    public HashMap<String,Object> modifyStatus(String id,String status);
    public String addBanner(Banner banner);
    public void removeBanner(String id);
}
