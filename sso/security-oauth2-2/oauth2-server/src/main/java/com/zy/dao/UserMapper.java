package com.zy.dao;

import com.zy.model.Permission;
import com.zy.model.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User queryByUsername(String username);

    List<Permission> queryUserPermission(Long id);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}