package com.zy.cn.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zy.cn.security.dao")
public class SpringbootApplicationSecurity {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplicationSecurity.class,args);
    }
}