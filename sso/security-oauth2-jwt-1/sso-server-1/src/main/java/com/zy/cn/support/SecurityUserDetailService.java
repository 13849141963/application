package com.zy.cn.support;

import com.zy.cn.dao.UserDAO;
import com.zy.cn.pojo.SysPermission;
import com.zy.cn.pojo.SysRole;
import com.zy.cn.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: application
 * @description: 自定义userdetails
 * @author: 狗蛋
 * @create: 2019-10-30 13:57
 */
@Service
public class SecurityUserDetailService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库查询用户的相关信息
        SysUser sysUser = userDAO.selectByName(username);
       if (sysUser == null){
            throw new UsernameNotFoundException(username);
        }
        // 用户权限集合
        List<SimpleGrantedAuthority> authorityList =new ArrayList();
        for (SysRole role : sysUser.getRoleList()) {
            for (SysPermission simpleGrantedAuthority : role.getPermissionList()) {
                // 将权限添加到authorityLis集合中
                authorityList.add(new SimpleGrantedAuthority(simpleGrantedAuthority.getCode()));
            }
        }
        return new User(username, sysUser.getPassword(), authorityList);
    }
}