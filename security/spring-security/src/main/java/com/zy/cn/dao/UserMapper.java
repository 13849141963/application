package com.zy.cn.dao;

import com.zy.cn.pojo.Permission;
import com.zy.cn.pojo.User;
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