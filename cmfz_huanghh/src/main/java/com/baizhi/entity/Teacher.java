package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: untitled
 * @description: 测试类
 * @author: Mr.huang
 * @create: 2019-08-05 17:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @ExcelIgnore
    private String id;
    @Excel(name="教师",needMerge = true)
    private String name;
    @ExcelCollection(name="158学生")
    private List<Student> students;
}