package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: untitled
 * @description: 对性别和城市封装
 * @author: Mr.huang
 * @create: 2019-08-06 17:29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChinaMap {
    private String title;
    private List<City> cities;
}