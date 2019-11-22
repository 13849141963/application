package com.zy.cn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @program: application
 * @description: 测试csv导出
 * @author: 狗蛋
 * @create: 2019-11-20 20:35
 */
@RestController
public class ExportCsvController {

    @GetMapping("/exportCsv")
    public CsvVO exportCsv() throws UnsupportedEncodingException {
        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setId("1");
        user1.setName("张三");
        user1.setAge("18");
        user1.setBirthday("20191119");
        user1.setEmail("zhangsan@163.com");
        user1.setPhone("12345678");

        User user2 = new User();
        user2.setId("2");
        user2.setName("李四");
        user2.setAge("18");
        user2.setBirthday("20191119");
        user2.setEmail("lsii@163.com");
        user2.setPhone("12345678");


        User user3 = new User();
        user3.setId("3");
        user3.setName("王五");
        user3.setBirthday("20191119");
        user3.setEmail("wangwu@163.com");
        user3.setPhone("12345678");
        users.add(user1);
        users.add(user2);
        users.add(user3);

        StringBuilder stringValue = new StringBuilder();
        stringValue.append("编号,姓名,年龄,生日,邮箱,手机"+"\n");
        for (User user : users) {
            stringValue.append(Optional.ofNullable(user.getId()).orElse("")
                    + "," + Optional.ofNullable(user.getName()).orElse("")
                    + "," + Optional.ofNullable(user.getAge()).orElse("")
                    + "," + Optional.ofNullable(user.getBirthday()).orElse("")
                    + "," + Optional.ofNullable(user.getEmail()).orElse("")
                    + "," + Optional.ofNullable(user.getPhone()).orElse("") + "\n");
        }
        byte[] base64String = Base64.getEncoder().encode(stringValue.toString().getBytes("GBK"));
        return new CsvVO("base64.csv",new String(base64String));
    }
}