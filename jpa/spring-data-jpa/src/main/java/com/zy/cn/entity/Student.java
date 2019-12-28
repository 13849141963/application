package com.zy.cn.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: application
 * @description: 学生
 * @author: 狗蛋
 * @create: 2019-12-22 15:01
 */
@Entity
@Table(name = "student")
public class Student {
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
     * 配多对多的关系映射
     * @ManyToMany 多对多表关系映射 cascade = CascadeType.ALL 配置级连操作
     * 配置中间表
     *  @JoinTable  name ：中间表的名称[关联表名称]
     *              joinColumns:配置当前对象在中间表的键
     *                  joinColumns数组
     *                      name: 外键名
     *                      referencedColumnName 参照主表的主键名称
     *              inverseJoinColumns：配置角色对象在中间表的外键
     *
     */
    @ManyToMany(targetEntity = Role.class,cascade = CascadeType.ALL)
    @JoinTable(name = "student_role",
                //joinColumns = 当前对象在中间表中的外键 student_id 为关联表中的用户外键 id 为学生的主键
                joinColumns = {@JoinColumn(name = "student_id",referencedColumnName = "id")},
                // inverseJoinColumns对方对象在中间表中的外键 role_id 为关联表中的角色外键  id 为角色的主键
                inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", roles=" + roles +
                '}';
    }
}