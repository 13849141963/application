package com.zy.cn.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: application
 * @description: 用户 一个用户对应多个联系人
 * @author: 狗蛋
 * @create: 2019-12-14 11:55
 */
@Entity
@Table(name = "user")
public class User {

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
     *  1.使用注解的形式配置多对一关系  ： @OneToMany
     *                              targetEntity:表示多(多对一)的那一方对应的类型
     *  2.配置外键()
     *   配置外键的过程中，配置到多的一方，就会在多的一方维护外键
     *   user_id 表示是联系人表的外键 对应用户表的主键
     *   @JoinColumn name 是联系人表的外键
     *               referencedColumnName 是外键关联用户表的关系主键
     */
//    @OneToMany(targetEntity = LinkMan.class)
//    @JoinColumn(name = "user_id",referencedColumnName = "id")
    // 由于双方都具备外键维护权 [测试类中可以都可以维护外键]
    //我们可以在多个一方放弃外键维护权也可以达到关联关系   mappedBy = 对方配置的关系属性名称
    //  cascade配置级连，可以设置所有方式 推荐配置all 区分主题进行设置级连属性
    //cascade 设计级连操作的属性 添加客户，同时自动添加联系人，相反雷同
            //fetch = FetchType.EAGER 表示多表级联查询是会立即加载
            //fetch = FetchType.LAZY 默认不会立即加载使用时在进行加载【sql查询】 建议方式，所以无需修改
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<LinkMan> linkMans = new HashSet<>();

    public Set<LinkMan> getLinkMans() {
        return linkMans;
    }

    public void setLinkMans(Set<LinkMan> linkMans) {
        this.linkMans = linkMans;
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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}