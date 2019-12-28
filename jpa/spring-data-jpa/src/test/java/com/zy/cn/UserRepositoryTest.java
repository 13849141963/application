package com.zy.cn;

import com.zy.cn.dao.UserRepository;
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
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    /**
     * 查询一个用户
     * findOne :直接加载 直接查询返回
     * getOne  :延迟加载 使用时再会进行查询返回
     */
    @Test
    public void testFindOne(){
        User user = userRepository.findOne(1L);
        System.out.println(user);
    }

    /**
     * 查询一个用户
     */
    @Test
    @Transactional
    public void testGetOne(){
        User user = userRepository.getOne(4L);
        System.out.println(user);
    }

    /***
     * 查询所有用户
     */
    @Test
    public void testFindAll(){
        List<User> users = userRepository.findAll();
        users.parallelStream().forEach(System.out::print);
    }


    /***
     * 添加用户
     */
    @Test
    public void testSave(){
        User user =  new User();
        user.setAge(18);
        user.setBirthday(new Date());
        user.setName("李思思");
        User save = userRepository.save(user);
        System.out.println(save);
    }

    /***
     * 更新用户 使用save方法时存在主键先查询，存在进行在更新，不存在则添加
     */
    @Test
    public void testUpdate(){
        User user =  new User();
        user.setAge(18);
        user.setBirthday(new Date());
        user.setName("李四");
        user.setId(2L);
        User save = userRepository.save(user);
        System.out.println(save);
    }


    /***
     * 根据用户删除用户 先查询在删除
     */
    @Test
    public void testDelete(){
        User user =  new User();
        user.setAge(18);
        user.setBirthday(new Date());
        user.setName("李思思");
        user.setId(3L);
        userRepository.delete(user);
    }

    /***
     * 根据用户删除用户 先查询在删除
     */
    @Test
    public void testDeleteId(){
        userRepository.delete(1L);
    }


    /***
     * 插入集合
     */
    @Test
    public void testAddList(){
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setName("淘宝");
        user1.setAge(99);
        user1.setBirthday(new Date());

        User user2 = new User();
        user2.setName("阿里云");
        user2.setAge(68);
        user2.setBirthday(new Date());

        users.add(user1);
        users.add(user2);
        List<User> saves = userRepository.save(users);
        saves.parallelStream().forEach(System.out::print);
    }



    /***
     * 查询记录数
     */
    @Test
    public void testCount(){
        long count = userRepository.count();
        System.out.println(count);
    }


    /***
     * 删除集合 先查询在删除
     */
    @Test
    public void testDeletes(){
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        users.add(user1);
        users.add(user2);
        userRepository.delete(users);
    }


    /***
     * 删除表的所有数据 先查询在删除
     */
    @Test
    public void testDeleteAll(){
        userRepository.deleteAll();
    }

    /***
     * 删除表的所有数据  先查询在删除
     */
    @Test
    public void testAllInBatch(){
        userRepository.deleteAllInBatch();
    }

    /***
     * 判断该主键是否存在
     */
    @Test
    public void testExists(){
        boolean exists = userRepository.exists(5L);
        System.out.println(exists);
    }


    /***
     * 查询所有并按照主键进行降序
     */
    @Test
    public void testFindAllSort(){
        List<User> users = userRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
        for (User user : users) {
            System.out.println(user);
        }
    }

}
