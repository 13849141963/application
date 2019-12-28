package com.zy.cn;

import com.zy.cn.dao.UserRepository;
import com.zy.cn.dao.UserRepositoryNamed;
import com.zy.cn.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
public class UserNamedRepositoryTest {

    @Autowired
    UserRepositoryNamed userRepository;


    @Test
    public void testFindOne(){
        User user = userRepository.findByName("阿里巴巴");
        System.out.println(user);
    }

    @Test
    public void findByNameLike(){
        List<User> users = userRepository.findByNameLike("%阿%");
        users.parallelStream().forEach(System.out::println);
    }

    @Test
    public void findByNameAndAge(){
        User user = userRepository.findByNameAndAge("阿里巴巴",20);
        System.out.println(user);
    }

    @Test
    public void findByNameLikeAndAge(){
        List<User> users = userRepository.findByNameLikeAndAge("%阿%",20);
        users.parallelStream().forEach(System.out::println);
    }


}
