package com.zy.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringBootApplicationRunStartMain {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationRunStartMain.class,args);
    }

    @RequestMapping("/demo")
    public String selectByPrimaryKey(){
        return "demo";
    }
}