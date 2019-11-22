package com.gzcr.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gzcr.sso.mapper")
public class CrSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrSsoApplication.class, args);
    }

}
