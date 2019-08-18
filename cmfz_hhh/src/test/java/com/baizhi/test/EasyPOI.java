package com.baizhi.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.Application;
import com.baizhi.entity.Student;
import com.baizhi.entity.Teacher;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: untitled
 * @description: 测试EasyPOI
 * @author: Mr.huang
 * @create: 2019-08-05 16:53
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EasyPOI {

    @Test
    public void test1(){
        ArrayList<Student> students = new ArrayList<>();
        Student student1= new Student("1", "刚刚", 18, new Date());
        Student student2= new Student("2", "刚刚", 18, new Date());
        Student student3= new Student("3", "刚刚", 18, new Date());
        Student student4= new Student("4", "刚刚", 18, new Date());
        Student student5= new Student("5", "刚刚", 18, new Date());
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("JAVA158", "学生"), Student.class, students);
        try {
            workbook.write(new FileOutputStream(new File("C://Users//Administrator//Desktop//easy.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test2(){
        ArrayList<Student> students = new ArrayList<>();
        Student student1= new Student("1", "刚刚", 18, new Date());
        Student student2= new Student("2", "刚刚", 18, new Date());
        Student student3= new Student("3", "刚刚", 18, new Date());
        Student student4= new Student("4", "刚刚", 18, new Date());
        Student student5= new Student("5", "刚刚", 18, new Date());
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        Teacher teacher1 = new Teacher("1","zcn",students);
        Teacher teacher2 = new Teacher("2","cpx",students);
        Teacher teacher3 = new Teacher("3","xxx",students);
        ArrayList<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher1);
        teachers.add(teacher2);
        teachers.add(teacher3);
        //参数：(一级标题，二级标题，表名)，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("JAVA158","学生"), Teacher.class, teachers);
        try {
            workbook.write(new FileOutputStream(new File("C://Users//Administrator//Desktop//easy.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test3(){
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(2);
        List<Object> teachers = null;
        try {
            teachers = ExcelImportUtil.importExcel(new FileInputStream(new File("C://Users//Administrator//Desktop//easy.xls")), Teacher.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Object teacher : teachers) {
            System.out.println(teacher);
        }
    }
}