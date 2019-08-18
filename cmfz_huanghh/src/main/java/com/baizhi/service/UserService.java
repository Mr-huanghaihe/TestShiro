package com.baizhi.service;

import com.baizhi.entity.ChinaMap;
import com.baizhi.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface UserService {

    public HashMap<String,Object> showAll(Integer page,Integer rows);

    public User showOneUser(String id);

    public HashMap<String,Object> modify(User user);

    public void exportAll();

    public void importUsers();

    public HashMap<String,Object> showUsers();

    public ArrayList<ChinaMap> showChinaMap();

    public void showGoEasy();

    public void add(User user);
}
