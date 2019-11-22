package com.zy.service.impl;

import com.zy.model.Permission;
import com.zy.model.User;
import com.zy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: application
 * @description:
 * @author: 狗蛋
 * @create: 2019-11-02 13:48
 */
@Service("userDetailService")
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.queryByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在～～～");
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Permission permission : userService.queryUserPermission(user.getId())) {
            authorityList.add(new SimpleGrantedAuthority(permission.getEnname()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorityList);
    }
}