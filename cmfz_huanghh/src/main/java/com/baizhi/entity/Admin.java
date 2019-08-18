package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private String admin_id;
    private String username;
    private String password;
    private String salt;

    private List<Role> roles;

}
