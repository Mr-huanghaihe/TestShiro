package com.baizhi.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao<T> {
    List<T> selectByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    void add(T t);

    void update(T t);

    void delete(String id);

    Integer selectCounts();
}
