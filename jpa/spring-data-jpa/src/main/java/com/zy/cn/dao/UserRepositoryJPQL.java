package com.zy.cn.dao;

import com.zy.cn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/***
 * 使用注解·Query 完成jpql语句查询
 */

public interface UserRepositoryJPQL extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    @Query("from User where name = ?")
    User findUser1(String name);

    @Query("from User where name = ? and age = ?")
    User findUser2(String name, Integer age);

    @Query("select o from User o where name = ?1 and age = ?2")
    User findUser3(String name, Integer age);

    @Query("select o from User o where name = :name and age = :age")
    User findUser4(@Param("name") String name, @Param("age") Integer age);

    @Query("select o from User o where name like %?1%")
    List<User> findLikeName(String name);

    @Query("update User set name = :name where id = :id ")
    @Modifying
    void updateUser(@Param("name") String name, @Param("id") Long id);

}
