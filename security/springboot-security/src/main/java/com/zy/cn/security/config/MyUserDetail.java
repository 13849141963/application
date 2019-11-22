package com.zy.cn.security.config;

import com.zy.cn.security.dao.UserMapper;
import com.zy.cn.security.pojo.Permission;
import com.zy.cn.security.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

/****
 * 自定义userDetails 查询用的信息
 */
@Component
public class MyUserDetail implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        User user = userMapper.queryByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("用户名不存在～～～");
        }
        // 查询用户相关权限
        List<Permission> permissions = userMapper.queryUserPermission(username);
        // 组装权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Permission permission : permissions) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPermTag());
            authorities.add(grantedAuthority);
        }
        if (!CollectionUtils.isEmpty(authorities)){
            user.setAuthorities(authorities);
        }
        System.out.println("username = [" + username + "]"+",authentication = "+ permissions );
        return user;
    }
}