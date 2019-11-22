package com.zy;

import com.zy.dao.UserMapper;
import com.zy.model.Permission;
import com.zy.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = Oauth2ServerApplication.class)
class Oauth2ServerApplicationTests {

	@Autowired
	UserMapper userMapper;

	@Test
	void contextLoads() {
        User admin = userMapper.queryByUsername("admin");
        System.out.println(admin);
    }

    @Test
    public  void contextLosads() {
        List<Permission> permissions = userMapper.queryUserPermission(37L);
        System.out.println(permissions);
    }

}
