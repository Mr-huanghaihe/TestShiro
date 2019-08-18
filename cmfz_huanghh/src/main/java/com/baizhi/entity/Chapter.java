package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter implements Serializable {
    private String id;
    private String url;
    private String size;
    private String duration;
    private Date up_date;
    private String album_id;
}
