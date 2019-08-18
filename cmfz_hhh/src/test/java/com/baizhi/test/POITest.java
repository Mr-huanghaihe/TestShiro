package com.baizhi.test;

import com.baizhi.Application;
import com.baizhi.entity.Admin;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontFormatting;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @program: untitled
 * @description: 导出excle文档
 * @author: Mr.huang
 * @create: 2019-08-05 11:29
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class POITest {
    @Test
    public void POI() {
        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作薄   参数：工作薄名字(sheet1,shet2....)
        HSSFSheet sheet = workbook.createSheet();
        //创建一行  参数：行下标(下标从0开始)
        HSSFRow row0 = sheet.createRow(0);
        //创建一个单元格  参数：单元格下标(下标从0开始)
        HSSFCell cell0 = row0.createCell(0);
        //给单元格设置内容
        cell0.setCellValue("你真棒！！！！！！！！！");
        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("C://Users//Administrator//Desktop//test.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void POI2() {
        Admin a1 = new Admin("1", "xiaoming", "1111");
        Admin a2 = new Admin("2", "xiaoyang", "1111");
        Admin a3 = new Admin("3", "xiaochen", "1111");
        Admin a4 = new Admin("4", "xiaozhang", "1111");
        Admin a5 = new Admin("5", "xiaogaigai", "1111");
        List<Admin> admins = Arrays.asList(a1, a2, a3, a4, a5);
        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作薄   参数：工作薄名字(sheet1,shet2....)
        HSSFSheet sheet = workbook.createSheet("管理员信息");

        //构建字体
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("楷体");
        font.setItalic(true);
        font.setUnderline(FontFormatting.U_SINGLE);
        //创建一个日期格式对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        //创建样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //将字体样式引入
        cellStyle.setFont(font);
        //将日期格式放入样式对象中
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));
        //文字居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        //设置列宽  参数：列索引，列宽(注意：单位为1/256)
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(1, 20 * 256);

        HSSFRow row0 = sheet.createRow(0);
        //设置行高   参数：行高(注意：单位为1/20,short类型)
        row0.setHeight((short) 600);

        //设置标题
        HSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue("管理员信息");
        cell0.setCellStyle(cellStyle);
        //合并行   参数：起始行,结束行,起始单元格,结束单元格
        CellRangeAddress address = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(address);


        //创建标题行
        HSSFRow row1 = sheet.createRow(1);
        String[] title = {"ID", "NAME", "PASS"};
        HSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row1.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(cellStyle);
        }

        //处理数据行
        for (int i = 0; i < admins.size(); i++) {
            HSSFRow row = sheet.createRow(i + 2);
            HSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(admins.get(i).getId());
            cell1.setCellStyle(cellStyle);
            HSSFCell cell2 = row.createCell(1);
            cell2.setCellValue(admins.get(i).getUsername());
            cell2.setCellStyle(cellStyle);
            HSSFCell cell3 = row.createCell(2);
            cell3.setCellValue(admins.get(i).getPassword());
            cell3.setCellStyle(cellStyle);
        }
        try {
            workbook.write(new FileOutputStream(new File("C://Users//Administrator//Desktop//test.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void POI3() {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("C://Users//Administrator//Desktop//test.xls"));
            HSSFSheet sheet = workbook.getSheet("管理员信息");
            System.out.println("最后一行下标为：" + sheet.getLastRowNum());
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Admin admin = new Admin();
                HSSFRow row = sheet.getRow(i);
                admin.setId(row.getCell(0).getStringCellValue());
                admin.setUsername(row.getCell(1).getStringCellValue());
                admin.setPassword(row.getCell(2).getStringCellValue());
                System.out.println(admin);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}