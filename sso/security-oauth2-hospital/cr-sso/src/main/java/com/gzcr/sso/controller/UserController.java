package com.gzcr.sso.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gzcr.sso.mapper.SysUserMapper;
import com.gzcr.sso.objects.dto.PasswordDTO;
import com.gzcr.sso.objects.entity.SysUser;
import com.gzcr.sso.utils.CheckUtils;
import com.gzcr.sso.utils.ValidataUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private SysUserMapper sysUserMapper;


    //工程测试接口 http://127.0.0.1:8076/user/selectUser
    @RequestMapping(value = "/selectUser", method = RequestMethod.GET)
    public List<SysUser> selectUser() {
        //检验手机号码
        boolean phone = ValidataUtils.isPhoneNumber("");
        //新增用户, 判断用户是否已经存在
        //
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");
        List<SysUser> users = sysUserMapper.selectByMap(map);
        users.forEach(System.out::println);

        return users;
    }

    //添加用户 - 常规测试成功
    @RequestMapping(value = "/insertUser", method = RequestMethod.POST)
    @Transactional
    public String insertUser(@Valid @RequestBody SysUser user) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = Wrappers.<SysUser>lambdaQuery();
        sysUserLambdaQueryWrapper.eq(SysUser::getPhone, user.getPhone());
        SysUser sysUser = sysUserMapper.selectOne(sysUserLambdaQueryWrapper);
        if (!ObjectUtil.isNull(sysUser)) {
            return "用户已经存在.";
        }
        int insert = sysUserMapper.insert(user);
        return insert > 0 ? "添加用户成功" : "添加用户失败";
    }

    //删除用户 - 逻辑删除
    public void deleteUser() {

    }

    //更新用户
    public void updateUser() {

    }

    //修改用户信息 - 常规测试成功
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @Transactional
    public String updateUserInfo(@Valid @RequestBody SysUser user) {
        //查询是否有这个用户
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = Wrappers.<SysUser>lambdaQuery();
        sysUserLambdaQueryWrapper.eq(SysUser::getPhone, user.getPhone());
        SysUser sysUser = sysUserMapper.selectOne(sysUserLambdaQueryWrapper);
        //判断这个用户是否可用
        if (0 != sysUser.getDelFlag()) {
            return "用户信息修改失败,当前帐户已被禁用";
        }
        int update = sysUserMapper.update(user, sysUserLambdaQueryWrapper);
        return update > 0 ? "用户信息修改成功" : "用户信息修改失败";
    }

    //修改用户密码 - 常规测试成功
    @RequestMapping(value = "/updateUserPassWord", method = RequestMethod.POST)
    public String updateUserPassWord(@Valid @RequestBody PasswordDTO password) {
        //根据手机号码验证用户是否存在
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = Wrappers.<SysUser>lambdaQuery();
        sysUserLambdaQueryWrapper.eq(SysUser::getPhone, password.getPhone());
        SysUser sysUser = sysUserMapper.selectOne(sysUserLambdaQueryWrapper);
        boolean notNull = ObjectUtil.isNotNull(sysUser);
        if (!notNull) {
            return "用户密码修改失败,当前帐户不存在";
        }
        if (0 != sysUser.getDelFlag()) {
            return "用户密码修改失败,当前帐户已被禁用";
        }
        //判断PasswordDTO里面的旧密码字段是否和数据库存储的一样
        if (!sysUser.getPassword().equals(password.getOldPassword())) {
            return "用户密码修改失败,旧密码填写不正确";
        }
        if (!sysUser.getPhone().equals(password.getPhone())) {
            return "用户密码修改失败,手机号码填写不正确";
        }
        sysUser.setPassword(password.getNewPassword());
        //新密码修改
        int updateFlag = sysUserMapper.update(sysUser, sysUserLambdaQueryWrapper);

        return updateFlag > 0 ? "用户密码修改成功" : "用户密码修改失败";
    }


    //查看用户信息 - 常规测试成功
    @RequestMapping(value = "/findUserInfo/{phone}", method = RequestMethod.GET)
    public SysUser findUserInfo(@PathVariable(value = "phone", required = true) String phone) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = Wrappers.<SysUser>lambdaQuery();
        LambdaQueryWrapper<SysUser> userWrapper = sysUserLambdaQueryWrapper.eq(SysUser::getPhone, phone);
        SysUser sysUser = sysUserMapper.selectOne(userWrapper);
        if (!ObjectUtil.isNotNull(sysUser)) {
            CheckUtils.checkNull(sysUser, "当前用户不存在.");
        }
        return sysUser;
    }

    //获取用户列表 - 常规测试成功  - - !!!!
    @RequestMapping(value = "/selectUserList", method = RequestMethod.GET)
    public List<SysUser> selectUserList() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);

        return sysUsers;
    }

    //用户更绑手机号
    public void updateUserPhone() {

    }

}
