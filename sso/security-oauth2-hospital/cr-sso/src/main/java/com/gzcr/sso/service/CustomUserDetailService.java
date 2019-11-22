package com.gzcr.sso.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzcr.sso.mapper.SysUserMapper;
import com.gzcr.sso.objects.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @program: application
 * @description: 自定义userDetailService实现UserDetailsService
 * @author:
 * @create: 2019-11-21
 */
@Service("userDetailService")
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper(sysUser);
        SysUser user = sysUserMapper.selectOne(queryWrapper);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在～～～");
        }
        // 权限相关集合
//        List<GrantedAuthority> authorityList = new ArrayList<>();
//        for (Permission permission : userService.queryUserPermission(user.getId())) {
//            authorityList.add(new SimpleGrantedAuthority(permission.getEnname()));
//        }
        return new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }
}