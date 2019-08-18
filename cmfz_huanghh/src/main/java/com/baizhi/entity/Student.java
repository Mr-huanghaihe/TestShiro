package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: untitled
 * @description: 测试类
 * @author: Mr.huang
 * @create: 2019-08-05 16:49
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Excel(name = "ID")
    private String id;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "年龄")
    private Integer age;
    @Excel(name = "生日",format = "yyyy-MM-dd")
    private Date birthday;

}