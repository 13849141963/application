package com.zy.cn.swagger;

import com.didispace.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@SpringBootApplication
public class SpringbootApplicationSwagger {
    public static void main( String[] args ) {
        SpringApplication.run(SpringbootApplicationSwagger.class,args);
    }
}
