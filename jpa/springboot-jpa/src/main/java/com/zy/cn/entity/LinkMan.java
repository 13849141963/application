package com.zy.cn.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: application
 * @description: 联系人对象 用来模拟一对多关系 一个用户对应多个联系人
 * @author: 狗蛋
 * @create: 2019-12-22 11:33
 */
@Entity
@Table(name = "linkman")
public class LinkMan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "birthday")
    private Date birthday;

    /***
     * 配置联系人到用户的多对一关系
     *  1.使用注解的形式配置多对一关系  ：@ManyToOne
     *                              targetEntity:表示一(多对一)的那一方对应的类型
     *  2.配置外键()
     *   配置外键的过程中，配置到多的一方，就会在多的一方维护外键
     *   user_id 表示是联系人表的外键 对应用户表的主键
     *   @JoinColumn name 是联系人表的外键
     *               referencedColumnName 是外键关联用户表的关系主键
     */
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "LinkMan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}