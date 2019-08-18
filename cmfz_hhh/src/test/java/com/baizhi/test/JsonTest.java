package com.baizhi.test;

import com.alibaba.fastjson.JSON;
import com.baizhi.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @program: untitled
 * @description: 测试各种类型JSON格式
 * @author: Mr.huang
 * @create: 2019-08-07 20:58
 */

public class JsonTest {

    @Test
    public void testJson(){
        /*String[] s1={"11","22","33"};
        int[] s2={11,22,33};
        String s = JSON.toJSONString(s1);
        String s3 = JSON.toJSONString(s2);
        System.out.println(s);
        System.out.println(s3);*/
        Admin admin = new Admin("1","huang","666666");
        String s = JSON.toJSONString(admin);
        System.out.println(s);
        Admin admin1 = new Admin("2","haihe","888888");
        List<Admin> admins =Arrays.asList(admin,admin1);
        String s1 = JSON.toJSONString(admins);
        System.out.println(s1);
        Admin huang = new Admin("3", "huang", "666666");
        Admin haihe = new Admin("4", "haihe", "888888");
        Map<String, Object> map = new HashMap<>();
        map.put("huang",huang);
        map.put("haihe",haihe);
        String s2 = JSON.toJSONString(map);
        System.out.println(s2);
    }
}