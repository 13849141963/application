package com.zy.cn;

import com.zy.cn.dao.LinkManRepository;
import com.zy.cn.dao.UserRepositoryOneToMany;
import com.zy.cn.entity.LinkMan;
import com.zy.cn.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @program: application
 * @description: 测试一对多关系数据库操作 一个用户对应多个联系人
 * @author: 狗蛋
 * @create: 2019-12-22 11:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserRepositoryOneToManyTest {

    @Autowired
    UserRepositoryOneToMany userRepositoryOneToMany;

    @Autowired
    LinkManRepository linkManRepository;

    /***
     * 执行的sql 如下
     * Hibernate: insert into user (age, birthday, name) values (?, ?, ?)
     * Hibernate: insert into linkman (age, birthday, name, user_id) values (?, ?, ?, ?)
     * Hibernate: update linkman set user_id=? where id=?
     */
    @Test
    @Transactional
    @Rollback(false)
    public void save(){

        User user = new User();
        user.setName("张三用户");

        LinkMan linkMan = new LinkMan();
        linkMan.setName("张三联系人");

        // 通过这种方式在添加时进行关联
        //配置用户与联系人的关系
        user.getLinkMans().add(linkMan);

        userRepositoryOneToMany.save(user);
        linkManRepository.save(linkMan);
    }



    /***
     * 执行的sql 如下 这种与上面一样 这种方式更好一点
     * Hibernate: insert into user (age, birthday, name) values (?, ?, ?)
     * Hibernate: insert into linkman (age, birthday, name, user_id) values (?, ?, ?, ?)
     */
    @Test
    @Transactional
    @Rollback(false)
    public void tets(){

        User user = new User();
        user.setName("李四用户");

        LinkMan linkMan = new LinkMan();
        linkMan.setName("李四联系人");

        // 通过这种方式在添加时进行关联
        //配置用户联系人与用户的关系 这种和上面的一样都是保存用户，联系人，以及关联关系
        linkMan.setUser(user);

        userRepositoryOneToMany.save(user);
        linkManRepository.save(linkMan);
    }



    /***
     * 级联添加
     * Hibernate: insert into user (age, birthday, name) values (?, ?, ?)
     *      * Hibernate: insert into linkman (age, birthday, name, user_id) values (?, ?, ?, ?)
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testADD(){

        User user = new User();
        user.setName("小七用户");

        LinkMan linkMan = new LinkMan();
        linkMan.setName("小七1联系人");

        // 通过这种方式在添加时进行关联
        linkMan.setUser(user);
        user.getLinkMans().add(linkMan);

        userRepositoryOneToMany.save(user);
    }


    /***
     * 级联删除 ：通过@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
     * cascade配置级连，可以设置所有方式
     * delete from linkman where id=?
     * Hibernate: delete from user where id=?
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testDelete(){
        User user = userRepositoryOneToMany.findOne(13L);
        userRepositoryOneToMany.delete(user);
    }

    /****
     * 对象导航查询 默认使用的是延迟加载的方式执行的 调用get方法不会立即执行 使用时进行sql查询
     * 一对多查询方式 默认使用延迟加载
     *  from user user0_ left outer join linkman linkmans1_ on user0_.id=linkmans1_.user_id where user0_.id=?
     *    修改默认加载的方式 在
     *    //fetch = FetchType.EAGER 表示多表级联查询是会立即加载
     *    //fetch = FetchType.LAZY 默认不会立即加载使用时在进行加载【sql查询】 建议方式，所以无需修改
     *    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
     *
     *
     *    Hibernate: select user0_.id as id1_4_0_, user0_.age as age2_4_0_, user0_.birthday as birthday3_4_0_, user0_.name as name4_4_0_ from user user0_ where user0_.id=?
     * Hibernate: select linkmans0_.user_id as user_id5_0_0_, linkmans0_.id as id1_0_0_, linkmans0_.id as id1_0_1_, linkmans0_.age as age2_0_1_, linkmans0_.birthday as birthday3_0_1_, linkmans0_.name as name4_0_1_, linkmans0_.user_id as user_id5_0_1_ from linkman linkmans0_ where linkmans0_.user_id=?
     */
    @Test
    @Transactional //解决 no session问题
    public void testGetAll(){
        // 查询一对多 用户对应的联系人·
        User user = userRepositoryOneToMany.findOne(15L);
        Set<LinkMan> linkMans = user.getLinkMans();
        for (LinkMan linkMan : linkMans) {
            System.out.println(linkMan);
        }
    }

    /****
     * 对象导航查询 默认使用的是延迟加载的方式执行的
     * 一对一查询方式 默认使用立即加载
     * 修改默认加载的方式 在 联系人的
     * 、  //fetch = FetchType.EAGER 表示多表级联查询是会立即加载
     *      *    //fetch = FetchType.LAZY 默认不会立即加载使用时在进行加载【sql查询】 建议方式，所以无需修改
     *      @ManyToOne(targetEntity = User.class)
     */
    @Test
    @Transactional //解决 no session问题
    public void testGetAll2(){
        // 查询一对一
        LinkMan one = linkManRepository.findOne(7L);
        User user = one.getUser();
        System.out.println(user);
    }



}