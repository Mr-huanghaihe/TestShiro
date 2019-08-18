package com.baizhi.dao;

import com.baizhi.entity.City;
import com.baizhi.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User>{
    public List<User> selectAll();
    public List<User> selectMan();
    public List<User> selectWoMan();
    public List<City> selectManGroupByCity();
    public List<City> selectWomanGroupByCity();
    public User selectOneUser(String id);
}
