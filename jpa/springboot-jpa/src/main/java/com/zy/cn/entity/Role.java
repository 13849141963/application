package com.zy.cn.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: application
 * @description: 角色
 * @author: 狗蛋
 * @create: 2019-12-22 15:02
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    /***
     * 配多对多的关系映射
     * @ManyToMany 多对多表关系映射
     * 多对多中放弃维护权 ： 被动的一方放弃维护权
     * mappedBy 对方配置映射关系的属性名称
     */
    @ManyToMany(mappedBy = "roles")
    private Set<Student> students = new HashSet<>();

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                '}';
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
}