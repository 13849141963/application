package com.zy.cn;

import com.zy.cn.dao.UserRepositorySpecification;
import com.zy.cn.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/**
 * @program: application
 * @description: UserRepository测试类
 * @author: 狗蛋
 * @create: 2019-12-14 11:59
 * 基本的增删改查
 */

public class UserRepositorySpecificationTest extends SpringbootJpaApplicationTest{

    @Autowired
    UserRepositorySpecification userRepository;

    /***
     * 使用Specification构造查询条件进行查询 根据用户名称进行查询
     * 1.实现Specification接口（提供泛型，查询的对象类型）
     * 2.实现toPredicate方法构造查询条件
     * Root<User> root 获取对象的属性名称
     * CriteriaQuery<?> criteriaQuery 自定义查询方式 一般用不到
     * CriteriaBuilder cb 组装查询条件
     */
    @Test
    public void testFindOne(){
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                // 获取比较的属性
                Path<Object> name = root.get("name");
                //构造查询条件
                //第一个参数：需要比较的方式
                //第二个参数：当前需要比较的取值
                return cb.equal(name,"阿里云");
            }
        };
        Optional<User> user = userRepository.findOne(specification);
        System.out.println(user.get());
    }



    /***
     * 使用Specification构造查询条件进行多条件查询 根据用户名称年龄进行多条件查询
     * 1.实现Specification接口（提供泛型，查询的对象类型）
     * 2.实现toPredicate方法构造查询条件
     * Root<User> root 获取对象的属性名称
     * CriteriaQuery<?> criteriaQuery 自定义查询方式 一般用不到
     * CriteriaBuilder cb 组装查询条件
     */
    @Test
    public void testFindNameAndAge(){
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                // 获取多条件比较的属性
                Path<Object> name = root.get("name");
                // 获取多条件比较的属性
                Path<Object> age = root.get("age");
                //构造查询条件
                //第一个参数：需要比较的方式
                //第二个参数：当前需要比较的取值
                Predicate predicate1 = cb.equal(name, "阿里云");
                //构造查询条件
                //第一个参数：需要比较的方式
                //第二个参数：当前需要比较的取值
                Predicate predicate2 = cb.equal(age, 68);
                //将多个查询条件进行组装到一起（多个条件）1.and 代表 name = ? and age = ? 2.or 代表 name = ? or age = ?
                Predicate and = cb.and(predicate1, predicate2);
                return and;
            }
        };
        Optional<User> user = userRepository.findOne(specification);
        System.out.println(user.get());
    }


    /***
     * 使用Specification构造查询条件进行用户名模糊查询 根据用户名称进行模糊查询
     * 1.实现Specification接口（提供泛型，查询的对象类型）
     * 2.实现toPredicate方法构造查询条件
     * Root<User> root 获取对象的属性名称
     * CriteriaQuery<?> criteriaQuery 自定义查询方式 一般用不到
     * CriteriaBuilder cb 组装查询条件
     * equal 可以直接使用path对象进行比较
     * gt,lt,ge,le,like需要根据指定path的参数类型之后，才能进行比较
     */
    @Test
    public void testFindLIKE(){
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                // 获取多条件比较的属性
                Path<Object> name = root.get("name");
                //构造查询条件
                // 查询方式 进行模糊查询
                Predicate like = cb.like(name.as(String.class), "%阿%");
                return like;
            }
        };
        List<User> users = userRepository.findAll(specification);
        users.parallelStream().forEach(System.out::println);
    }


    /***
     * 使用Specification构造查询条件进行用户名模糊查询，排序 根据用户名称进行模糊查询，排序
     * 1.实现Specification接口（提供泛型，查询的对象类型）
     * 2.实现toPredicate方法构造查询条件
     * Root<User> root 获取对象的属性名称
     * CriteriaQuery<?> criteriaQuery 自定义查询方式 一般用不到
     * CriteriaBuilder cb 组装查询条件
     * equal 可以直接使用path对象进行比较
     * gt,lt,ge,le,like需要根据指定path的参数类型之后，才能进行比较
     */
    @Test
    public void testFindLIKESort(){
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                // 获取多条件比较的属性
                Path<Object> name = root.get("name");
                //构造查询条件
                // 查询方式 进行模糊查询
                Predicate like = cb.like(name.as(String.class), "%阿%");
                return like;
            }
        };
        // 添加排序
        // 创建排序需要调用构造方法实例化sort对象
        // 第一个参数 排序的顺序
        // 第二个参数 排序的属性名称
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<User> users = userRepository.findAll(specification,sort);
        for (User user : users) {
            System.out.println(user);
        }
    }



    /***
     * 分页查询
     */
    @Test
    public void testFindPage(){
        // 添加分页
        // 创建排序需要调用构造方法实例化sort对象
        // 第一个参数 排序的顺序
        // 第二个参数 排序的属性名称
        PageRequest pageable = PageRequest.of(1, 1);
        Page<User> all = userRepository.findAll(pageable);
        System.out.println("all = " + all);
        System.out.println("列表集合 = " + all.getContent());
        System.out.println("总页数 = " + all.getTotalPages());
        System.out.println("总条数 = " + all.getTotalElements());
        System.out.println("getSize = " + all.getNumber());
    }


}
