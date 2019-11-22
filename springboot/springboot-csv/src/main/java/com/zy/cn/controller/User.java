package com.zy.cn.controller;

import java.io.Serializable;

/**
 * @program: application
 * @description: 用户
 * @author: 狗蛋
 * @create: 2019-11-20 20:34
 */
public class User implements Serializable {
    private String id;
    private String name;
    private String age;
    private String birthday;
    private String email;
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}