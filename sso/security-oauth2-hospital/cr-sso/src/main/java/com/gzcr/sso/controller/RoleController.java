package com.gzcr.sso.controller;/**
 * @author RedHawk
 * @create 2019-11-20 11:02
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <Description> <br>
 *
 * @author redhawk<br>
 * @taskId:   <br>
 * @version 1.0<br>
 * @createDate 2019/11/20 11:02 <br>
 * @see com.gzcr.sso.controller <br>
 */
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {


    //新增角色
    public void insertRole(){
    //新增角色, 可以没有权限


    }
    //删除角色
    public void deleteRole(Long[] ids){
        //直接写一个批量删除就好了
    }
    //修改角色
    public void updateRole(){

    }
    //查询角色
    public void selectRole(){

    }
    //角色赋值用户
    public void roleAllotUser(){

        //将角色id,和用户id,插到role_user表里面
    }

}
