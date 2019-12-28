package com.zy.cn.dao;

import com.zy.cn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface UserRepositoryNamed extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    /**
     * 根据方法命名规则进行查询一个用户
     * @param name
     * @return
     */
    User findByName(String name);

    /**
     * 根据方法命名规则进行模糊查询
     * @param name
     * @return
     */
    List<User> findByNameLike(String name);

    /**
     * 根据方法命名规则进行多条件查询
     * @param name
     * @return
     */
    User findByNameAndAge(String name, Integer age);

    /**
     * 根据方法命名规则进行多条件查询
     * @param name
     * @return
     */
    List<User> findByNameLikeAndAge(String name, Integer age);
}
