package com.zy.cn.dao;

import com.zy.cn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepositorySpecification extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
}
