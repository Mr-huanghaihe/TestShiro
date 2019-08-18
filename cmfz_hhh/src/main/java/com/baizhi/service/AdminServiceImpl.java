package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String,Object> queryOne(Admin admin,String code, HttpSession session) {
        String imgCode = (String) session.getAttribute("code");
        HashMap<String, Object> map = new HashMap<>();
        Admin a=adminDao.selectByname(admin.getUsername());
        if(imgCode.equals(code)){
            if(a!=null){
                if(admin.getPassword().equals(a.getPassword())){
                    map.put("success","200");
                    map.put("message","登录成功！");
                    session.setAttribute("loginAdmin",a);
                }else{
                    map.put("success","400");
                    map.put("message","密码错误！");
                }
            }else{
                map.put("success","400");
                map.put("message","用户不存在！");
            }
        }else{
            map.put("success","400");
            map.put("message","验证码错误！");
        }
        return map;
    }
}
