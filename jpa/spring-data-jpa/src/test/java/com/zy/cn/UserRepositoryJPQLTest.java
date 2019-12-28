package com.zy.cn;

import com.zy.cn.dao.UserRepositoryJPQL;
import com.zy.cn.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @program: application
 * @description: UserRepository测试类
 * @author: 狗蛋
 * @create: 2019-12-14 11:59
 * 基本的增删改查
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserRepositoryJPQLTest {

    @Autowired
    UserRepositoryJPQL userRepository;


    @Test
    public void testFindOne(){
        User user = userRepository.findUser1("阿里云");
        System.out.println(user);
    }

    @Test
    public void testFindUSer2(){
        User user = userRepository.findUser2("阿里云",68);
        System.out.println(user);
    }


    @Test
    public void testFindUSer3(){
        User user = userRepository.findUser3("阿里云",68);
        System.out.println(user);
    }


    @Test
    public void testFindUSer4(){
        User user = userRepository.findUser4("阿里云",68);
        System.out.println(user);
    }


    @Test
    @Scheduled
    public void testFindLikes(){
        List<User> users = userRepository.findLikeName("阿");
        users.parallelStream().forEach(System.out::print);
    }

    /***
     * 默认之行结束之后自动回滚事务 ，可以加上@Rollback注解
     */
    @Test
    @Rollback(value = false)
    @Transactional //添加事务的注解
    public void testUpdateUser(){
        userRepository.updateUser("阿里巴巴", 5L);
    }



}
