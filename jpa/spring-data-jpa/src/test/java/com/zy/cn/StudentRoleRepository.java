package com.zy.cn;

import com.zy.cn.dao.RoleRepository;
import com.zy.cn.dao.StudentRepository;
import com.zy.cn.entity.Role;
import com.zy.cn.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: application
 * @description: 多对多关系映射
 * @author: 狗蛋
 * @create: 2019-12-22 15:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StudentRoleRepository {


    @Autowired
    StudentRepository studentRepository;
    @Autowired
    RoleRepository roleRepository;

    /***
     * 测试添加多对多关系数据【放弃维护关系】
     * 保存 保存用户，保存角色，同时保存关联关系
     * Hibernate: insert into student (age, birthday, name) values (?, ?, ?)
     * Hibernate: insert into role (name) values (?)
     * Hibernate: insert into student_role (student_id, role_id) values (?, ?)
     */
    @Test
    @Transactional
    @Rollback(false)
    public void selectByPrimaryKey(){
        Student student = new Student();
        student.setName("张三学生");

        Role role = new Role();
        role.setName("班干部");

        student.getRoles().add(role);

        role.getStudents().add(student);

        studentRepository.save(student);

        roleRepository.save(role);
    }

    /***
     * 测试级联添加 设置属性cascade = CascadeType.ALL 配置级连操作
     * Hibernate: insert into student (age, birthday, name) values (?, ?, ?)
     * Hibernate: insert into role (name) values (?)
     * Hibernate: insert into student_role (student_id, role_id) values (?, ?)
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd() {
        Student student = new Student();
        student.setName("lisi学生");

        Role role = new Role();
        role.setName("组长");

        student.getRoles().add(role);

        role.getStudents().add(student);

        studentRepository.save(student);
    }

    /***
     * 测试级联删除 设置属性cascade = CascadeType.ALL 配置级连操作
     * Hibernate: delete from student_role where student_id=?
     * Hibernate: delete from role where id=?
     * Hibernate: delete from student where id=?
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testDelete() {
        Student one = studentRepository.findOne(4L);
        studentRepository.delete(one);
    }


}