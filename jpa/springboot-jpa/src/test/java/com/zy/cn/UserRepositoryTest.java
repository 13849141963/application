package com.zy.cn;

import com.zy.cn.dao.UserRepository;
import com.zy.cn.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @program: application
 * @description: UserRepository测试类
 * @author: 狗蛋
 * @create: 2019-12-14 11:59
 * 基本的增删改查
 */
public class UserRepositoryTest extends  SpringbootJpaApplicationTest{

    @Autowired
    UserRepository userRepository;

    /**
     * 查询一个用户
     * findOne :直接加载 直接查询返回
     * getOne  :延迟加载 使用时再会进行查询返回
     */
    @Test
    public void testFindSpecification(){
        Optional<User> user = userRepository.findOne(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> id = root.get("id");
                Predicate equal = criteriaBuilder.equal(id,4L);
                return equal;
            }
        });
        System.out.println(user.get());
    }

    /**
     * 查询一个用户
     */
    @Test
    public void testFindOneExample(){
        User user = new User();
        user.setName("张三用户");
        user.setId(10L);
        // 创建实例
        Example<User> example = Example.of(user);
        // 查询
        Optional<User> one = userRepository.findOne(example);
        System.out.println(one.get());
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
        user.setId(10L);
        User save = userRepository.save(user);
        System.out.println(save);
    }


    /***
     * 根据用户删除用户 先查询在删除
     * 测试中只要添加事务注解 就需要添加@Rollback(false)设置不需要回滚
     */
    @Test
    public void testDelete(){
        User user = userRepository.getOne(17L);
        userRepository.delete(user);
    }

    /***
     * 根据用户删除用户 先查询在删除
     * 设置了级联 删除用户也会删除用户对应的联系人集合
     * Hibernate: select user0_.id as id1_4_0_, user0_.age as age2_4_0_, user0_.birthday as birthday3_4_0_, user0_.name as name4_4_0_ from user user0_ where user0_.id=?
     * Hibernate: select linkmans0_.user_id as user_id5_0_0_, linkmans0_.id as id1_0_0_, linkmans0_.id as id1_0_1_, linkmans0_.age as age2_0_1_, linkmans0_.birthday as birthday3_0_1_, linkmans0_.name as name4_0_1_, linkmans0_.user_id as user_id5_0_1_ from linkman linkmans0_ where linkmans0_.user_id=?
     * Hibernate: delete from linkman where id=?
     * Hibernate: delete from user where id=?
     */
    @Test
    public void testDeleteId(){
        userRepository.deleteById(16L);
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
        List<User> saves = userRepository.saveAll(users);
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
        user1.setId(18L);

        User user2 = new User();
        user2.setId(19L);

        users.add(user1);
        users.add(user2);
        userRepository.deleteAll(users);
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
        boolean exists = userRepository.existsById(1L);
        System.out.println(exists);
    }


    /***
     * 查询所有并按照主键进行降序
     */
    @Test
    public void testFindAllSort(){
        List<User> users = userRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        for (User user : users) {
            System.out.println(user);
        }
    }

}
