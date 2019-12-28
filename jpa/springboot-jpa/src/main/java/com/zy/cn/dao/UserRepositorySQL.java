package com.zy.cn.dao;

import com.zy.cn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/***
 * 使用sql语句查询
 */

public interface UserRepositorySQL extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    /**
     * 使用失sql 查询 nativeQuery 用来配置查询方式
     * nativeQuery 就是设置使用sql查询 值为true使用sql进行查询 值为false使用JPQL进行查询
     * @param name
     * @return
     */
    @Query(nativeQuery = true, value = "select * from user where name = ?")
    User findUser1(String name);

    @Query(nativeQuery = true, value = "select * from user where name like %?1%")
    List<User> findLike(String name);
}
