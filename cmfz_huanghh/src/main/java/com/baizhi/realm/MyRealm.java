
package com.baizhi.realm;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Authority;
import com.baizhi.entity.Role;
import com.baizhi.service.AdminService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: untitled
 * @description: 自定义认证授权Realm
 * @author: Mr.huang
 * @create: 2019-08-13 17:18
 */
public class MyRealm extends AuthorizingRealm {
    @Resource
    private AdminService adminService;
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //根据token获取身份信息
        String username = (String) authenticationToken.getPrincipal();

        Admin admin = adminService.queryByUsername(username);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin.getUsername(), admin.getPassword(), ByteSource.Util.bytes(admin.getSalt()), this.getName());
        return authenticationInfo;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取主身份
        String username = (String) principalCollection.getPrimaryPrincipal();

        //根据主身份查询有哪些角色
        //根据角色查询有哪些权限
        Admin admin = adminService.queryOneForShiro(username);

        //创建角色集合
        ArrayList<String> roles = new ArrayList<>();
        //创建权限集合
        ArrayList<String> permissions = new ArrayList<>();

        //获取角色集合
        List<Role> list = admin.getRoles();
        //遍历角色集合
        for (Role role : list) {
            String role_name = role.getRole_name();
            //将角色放入新创建角色集合
            roles.add(role_name);

            //由角色获取权限集合
            List<Authority> authorities = role.getAuthorities();
            //遍历权限集合
            for (Authority authority : authorities) {
                String authority_name = authority.getAuthority_name();
                //将权限放入新创建权限集合
                permissions.add(authority_name);
            }
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //赋予角色
        authorizationInfo.addRoles(roles);
        //赋予权限
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }
}