package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public interface AdminService {
    /**
     *
     * @param admin 登录的管理员
     * @param code 验证码
     * @param session 储存管理员
     * @return 信息集合
     */
    public HashMap<String,Object> queryOne(Admin admin,String code, HttpSession session);
    Admin queryByUsername(String username);

    Admin queryOneForShiro(String username);
}
