package com.zy.cn.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: application
 * @description: 导入csv存储数据库
 * @author: 狗蛋
 * @create: 2019-11-20 22:13
 */
@RestController
public class ImportCsvController {


    @PostMapping("/importCsv")
    public String importCsv(@RequestParam(value = "file") MultipartFile file){

        return "";
    }
}