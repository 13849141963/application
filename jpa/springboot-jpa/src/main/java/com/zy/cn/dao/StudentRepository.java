package com.zy.cn.dao;

import com.zy.cn.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @program: application
 * @description: 学生 用来解决多对多关系 学生对应角色
 * @author: 狗蛋
 * @create: 2019-12-22 15:37
 */
public interface StudentRepository extends JpaRepository<Student,Long>, JpaSpecificationExecutor<Student> {
}