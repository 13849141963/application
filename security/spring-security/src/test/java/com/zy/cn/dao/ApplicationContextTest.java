package com.zy.cn.dao;

import com.zy.cn.pojo.Permission;
import com.zy.cn.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ApplicationContextTest {


    @Autowired
    UserMapper userMapper;

    @Test
    public void queryByUsername(){
        User user = userMapper.queryByUsername("zs");
        System.out.println(user);
    }

    @Test
    public void queryUserPermission(){
        List<Permission> permissions = userMapper.queryUserPermission("zs");
        System.out.println(permissions);
    }


    @Test
    public void update(){
        User user = new User();
        user.setUsername("zs");
        user.setPassword(new BCryptPasswordEncoder().encode("123"));
        userMapper.updatePassword(user);
    }

}