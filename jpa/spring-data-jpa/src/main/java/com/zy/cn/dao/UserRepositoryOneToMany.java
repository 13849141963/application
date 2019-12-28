package com.zy.cn.dao;

import com.zy.cn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/***
 * 处理一对多关系
 */
public interface UserRepositoryOneToMany extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
}
