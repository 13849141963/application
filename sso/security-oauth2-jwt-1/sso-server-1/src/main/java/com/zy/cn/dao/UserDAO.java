package com.zy.cn.dao;

import com.zy.cn.pojo.SysPermission;
import com.zy.cn.pojo.SysRole;
import com.zy.cn.pojo.SysUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.Arrays;

/**
 * @program: application
 * @description: 操作数据库的userMapper
 * @author: 狗蛋
 * @create: 2019-11-03 11:11
 */
@Repository
public class UserDAO {

    private SysRole admin = new SysRole("ADMIN", "管理员");
    private SysRole developer = new SysRole("DEVELOPER", "开发者");
    {
        SysPermission p1 = new SysPermission();
        p1.setCode("memberExport");
        p1.setName("会员列表导出");
        p1.setUrl("/member/export");

        SysPermission p2 = new SysPermission();
        p2.setCode("BookList");
        p2.setName("图书列表");
        p2.setUrl("/book/list");


        admin.setPermissionList(Arrays.asList(p1, p2));
        developer.setPermissionList(Arrays.asList(p1));
    }

    public SysUser selectByName(String username) {
        /***
         * 模拟从数据库查询用户
         */
        if ("zs".equals(username)) {
            SysUser sysUser = new SysUser("zs", "$2a$10$xoowdHtj8IrveuV1YdBWG.HmHc3Vy4jmF3ylimjuN.hTy2mrsMjwi");
            sysUser.setRoleList(Arrays.asList(admin, developer));
            return sysUser;
        }else if ("ls".equals(username)) {
            SysUser sysUser = new SysUser("ls", "$2a$10$xoowdHtj8IrveuV1YdBWG.HmHc3Vy4jmF3ylimjuN.hTy2mrsMjwi");
            sysUser.setRoleList(Arrays.asList(developer));
            return sysUser;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}