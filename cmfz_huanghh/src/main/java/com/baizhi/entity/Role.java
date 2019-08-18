package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: untitled
 * @description: 身份对应的角色
 * @author: Mr.huang
 * @create: 2019-08-13 17:42
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private String role_id;
    private String role_name;

    private List<Authority> authorities;
}