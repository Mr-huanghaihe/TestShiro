package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public interface AdminService {
    public HashMap<String,Object> queryOne(Admin admin,String code, HttpSession session);
}
