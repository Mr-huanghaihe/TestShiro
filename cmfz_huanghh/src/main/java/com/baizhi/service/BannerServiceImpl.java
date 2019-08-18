package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.redisCache.AddCache;
import com.baizhi.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class BannerServiceImpl implements BannerService{

    @Autowired
    private BannerDao bannerDao;

    @AddCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> showAll(Integer page, Integer rows) {
        Integer start=(page-1)*rows;
        List<Banner> banners = bannerDao.selectAll(start,rows);
        HashMap<String, Object> map = new HashMap<>();
        //records  总条数
        Integer counts = bannerDao.selectCounts();
        //total   总页数
        Integer total=counts % rows==0?counts / rows:counts / rows+1;

        map.put("records",counts);
        map.put("total",total);
        //page    当前页
        map.put("page",page);
        //row    数据
        map.put("rows",banners);
        return map;
    }

    @Override
    public String  modifyBanner(Banner banner) {
        bannerDao.updateBanner(banner);
        return banner.getId();
    }

    @Override
    public HashMap<String,Object> modifyStatus(String id,String status) {
        Banner banner = bannerDao.selectById(id);
        HashMap<String, Object> map=new HashMap<String, Object>();
        try {
            banner.setStatus(status);
            bannerDao.updateStatus(banner);
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
    public String addBanner(Banner banner) {
        String uuid=UUIDUtil.getUUID();
        banner.setId(uuid);
        banner.setStatus("正常");
        banner.setUp_date(new Date());
        bannerDao.insertOneBanner(banner);
        return uuid;
    }

    @Override
    public void removeBanner(String id) {
        bannerDao.deleteOneBanner(id);
    }
}
