package com.zy.cn;

import com.zy.cn.dao.UserRepositorySQL;
import com.zy.cn.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
public class UserRepositorySQLTest {

    @Autowired
    UserRepositorySQL userRepository;

    /**
     * 查询一个用户
     * findOne :直接加载 直接查询返回
     * getOne  :延迟加载 使用时再会进行查询返回
     */
    @Test
    public void testFindOne(){
        User user = userRepository.findUser1("阿里巴巴");
        System.out.println(user);
    }

    @Test
    public void testFindLike(){
        List<User> users = userRepository.findLike("阿");
        users.parallelStream().forEach(System.out::print);
    }



}
