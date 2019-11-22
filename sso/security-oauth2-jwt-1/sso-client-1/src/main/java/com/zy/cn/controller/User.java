package com.zy.cn.controller;

/**
 * @program: application
 * @description: yonghu
 * @author: 狗蛋
 * @create: 2019-10-30 15:51
 */
public class User {

    private String name;

    private String code;

    private String mobile;

    private Integer gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public Integer getGender() {
        return gender;
    }
}