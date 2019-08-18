package com.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import com.alibaba.fastjson.JSONObject;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.ChinaMap;
import com.baizhi.entity.City;
import com.baizhi.entity.User;
import com.baizhi.redisCache.AddCache;
import com.baizhi.utils.UUIDUtil;
import io.goeasy.GoEasy;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;

/**
 * @program: untitled
 * @description: About user and guru
 * @author: Mr.huang
 * @create: 2019-08-01 18:44
 */

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
    @AddCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> showAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        List<User> users = userDao.selectByPage((page - 1) * rows, rows);
        Integer counts = userDao.selectCounts();
        Integer total=counts%rows==0?counts/rows:counts/rows+1;
        map.put("records",counts);
        map.put("total",total);
        map.put("page",page);
        map.put("rows",users);
        return map;
    }

    @Override
    public User showOneUser(String id) {
        User user = userDao.selectOneUser(id);
        return user;
    }

    @Override
    public HashMap<String,Object> modify(User user) {
        HashMap<String, Object> map=new HashMap<String, Object>();
        try {
            userDao.update(user);
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
    public void exportAll() {
        List<User> users = userDao.selectAll();
        for (User user : users) {
            String path=user.getPhoto();
            user.setPhoto("E:/untitled/cmfz_huanghh/src/main/webapp/bootstrap/img/"+path);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "用户信息"), User.class, users);
        try {
            workbook.write(new FileOutputStream(new File("C://Users//Administrator//Desktop//user.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importUsers() {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<User> users = null;
        try {
            users = ExcelImportUtil.importExcel(new FileInputStream(new File("C://Users//Administrator//Desktop//users.xls")), User.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (User user : users) {
            userDao.add(user);
        }
    }

    @Override
    public HashMap<String, Object> showUsers() {
        HashMap<String, Object> map = new HashMap<>();
        List<User> mans = userDao.selectMan();
        String[] months = {"01月","02月","03月","04月","05月","06月","07月","08月","09月","10月","11月","12月"};
        map.put("month",months);
        List<Integer> counts = new ArrayList<>();
        for (String month : months) {
            Integer manCount=0;
            for (User man : mans) {
                Date date = man.getReg_date();
                String mon=String.format("%tm",date)+"月";
                if(month.equals(mon)) manCount++;
            }
            counts.add(manCount);
        }
        map.put("man",counts);


        List<Integer> countss = new ArrayList<>();
        List<User> womans = userDao.selectWoMan();
        for (String month : months) {
            Integer womanCount=0;
            for (User woman : womans) {
                Date date = woman.getReg_date();
                String mon=String.format("%tm",date)+"月";
                if(month.equals(mon)) womanCount++;
            }
            countss.add(womanCount);
        }
        map.put("woman",countss);
        return map;
    }

    @Override
    public ArrayList<ChinaMap> showChinaMap() {
        List<City> citiesBoy = userDao.selectManGroupByCity();
        List<City> citiesGirl = userDao.selectWomanGroupByCity();
        ChinaMap chinaMap1 = new ChinaMap("man",citiesBoy);
        ChinaMap chinaMap2 = new ChinaMap("woman",citiesGirl);
        ArrayList<ChinaMap> chinaMaps = new ArrayList<>();
        chinaMaps.add(chinaMap1);
        chinaMaps.add(chinaMap2);
        return chinaMaps;
    }

    @Override
    public void showGoEasy() {
        JSONObject jsonObject = new JSONObject();

        List<User> mans = userDao.selectMan();
        String[] months = {"01月","02月","03月","04月","05月","06月","07月","08月","09月","10月","11月","12月"};
        jsonObject.put("month",months);

        List<Integer> counts = new ArrayList<>();
        for (String month : months) {
            Integer manCount=0;
            for (User man : mans) {
                Date date = man.getReg_date();
                String mon=String.format("%tm",date)+"月";
                if(month.equals(mon)) manCount++;
            }
            counts.add(manCount);
        }
        jsonObject.put("man",counts);

        List<Integer> countss = new ArrayList<>();
        List<User> womans = userDao.selectWoMan();
        for (String month : months) {
            Integer womanCount=0;
            for (User woman : womans) {
                Date date = woman.getReg_date();
                String mon=String.format("%tm",date)+"月";
                if(month.equals(mon)) womanCount++;
            }
            countss.add(womanCount);
        }
        jsonObject.put("woman",countss);
        String content = jsonObject.toJSONString();
        //发布消息  发布地址，appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-5bf80b1620d54596b737f5db10f8136b");
        //参数: 管道(标识)名称,发布的内容
        goEasy.publish("channel1", content);
    }

    @Override
    public void add(User user) {
        String uuid = UUIDUtil.getUUID();
        user.setId(uuid);
        user.setCity("上海");
        user.setReg_date(new Date());
        user.setSex("女");
        userDao.add(user);
        JSONObject jsonObject = new JSONObject();

        List<User> mans = userDao.selectMan();
        String[] months = {"01月","02月","03月","04月","05月","06月","07月","08月","09月","10月","11月","12月"};
        jsonObject.put("month",months);

        List<Integer> counts = new ArrayList<>();
        for (String month : months) {
            Integer manCount=0;
            for (User man : mans) {
                Date date = man.getReg_date();
                String mon=String.format("%tm",date)+"月";
                if(month.equals(mon)) manCount++;
            }
            counts.add(manCount);
        }
        jsonObject.put("man",counts);

        List<Integer> countss = new ArrayList<>();
        List<User> womans = userDao.selectWoMan();
        for (String month : months) {
            Integer womanCount=0;
            for (User woman : womans) {
                Date date = woman.getReg_date();
                String mon=String.format("%tm",date)+"月";
                if(month.equals(mon)) womanCount++;
            }
            countss.add(womanCount);
        }
        jsonObject.put("woman",countss);
        String content = jsonObject.toJSONString();
        //发布消息  发布地址，appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-5bf80b1620d54596b737f5db10f8136b");
        //参数: 管道(标识)名称,发布的内容
        goEasy.publish("channel1", content);
    }

}