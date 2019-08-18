package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: untitled
 * @description: Article from Guru
 * @author: Mr.huang
 * @create: 2019-08-01 17:43
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {
    private String id;
    private String title;
    private String picture;
    private String content;
    private Date up_date;
    private String guruId;
    private String status;
}