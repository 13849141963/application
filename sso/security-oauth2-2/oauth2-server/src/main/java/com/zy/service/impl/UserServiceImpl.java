package com.zy.service.impl;

import com.zy.dao.UserMapper;
import com.zy.model.Permission;
import com.zy.model.User;
import com.zy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: application
 * @description:
 * @author: 狗蛋
 * @create: 2019-11-02 11:59
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryByUsername(String username) {
        return userMapper.queryByUsername(username);
    }

    @Override
    public List<Permission> queryUserPermission(Long id) {
        return userMapper.queryUserPermission(id);
    }
}