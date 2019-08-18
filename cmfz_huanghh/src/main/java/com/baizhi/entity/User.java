package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: untitled
 * @description: User and Guru
 * @author: Mr.huang
 * @create: 2019-08-01 17:38
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Excel(name="ID")
    private String id;
    @Excel(name="姓名")
    private String name;
    @Excel(name="法名")
    private String ahama;
    @Excel(name="手机号",width = 15)
    private String phone;
    @ExcelIgnore
    private String password;
    @ExcelIgnore
    private String salt;
    @Excel(name="图片",type = 2,width = 40,height = 20)
    private String photo;
    @Excel(name="性别")
    private String sex;
    @Excel(name="城市")
    private String city;
    @Excel(name="签名")
    private String sign;
    @Excel(name="状态")
    private String status;
    @Excel(name="注册时间",format = "yyyy-MM-dd")
    private Date reg_date;
    @Excel(name="上师ID")
    private String guruId;
}