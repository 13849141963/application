package com.zy.cn.dao;

import com.zy.cn.entity.LinkMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @program: application
 * @description: 联系人表 对应关系 ： 用户-》联系人 一个用户对应多个联系人
 * @author: 狗蛋
 * @create: 2019-12-22 11:43
 */
public interface LinkManRepository extends JpaRepository<LinkMan,Long>, JpaSpecificationExecutor<LinkMan> {
}