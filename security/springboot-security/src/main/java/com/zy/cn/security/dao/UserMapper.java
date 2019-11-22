package com.zy.cn.security.dao;

import com.zy.cn.security.pojo.Permission;
import com.zy.cn.security.pojo.User;

import java.util.List;

public interface UserMapper {
    /***
     * 修改密码
     * @param user
     */
    void updatePassword(User user);

    /***
     * 查询用户信息
     * @param username
     */
    User queryByUsername(String username);

    /****
     * 查询用户的权限集合
     * @param username
     * @return
     */
    List<Permission> queryUserPermission(String username);
}