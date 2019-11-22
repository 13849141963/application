package com.zy.service;

import com.zy.model.Permission;
import com.zy.model.User;

import java.util.List;

/**
 * @program: application
 * @description: `
 * @author: 狗蛋
 * @create: 2019-11-02 11:58
 */
public interface UserService {

    User queryByUsername(String username);

    List<Permission> queryUserPermission(Long id);
}