package com.zy.cn.swagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoSwagger2Controller {

    @GetMapping("/swagger2")
    public String swagger2(){
        return "Hello Swagger2~~";
    }
}