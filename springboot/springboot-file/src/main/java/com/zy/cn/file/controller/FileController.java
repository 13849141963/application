package com.zy.cn.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.UUID;

@Controller
public class FileController {

    @RequestMapping("/file")
    public String returnFile(){
        return "file";
    }


    /***
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload (@RequestParam("file") MultipartFile file) {
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        // 获取后缀名
        // String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件保存路径
        String filePath = "/Users/apple/Documents/";
        // 文件重命名，防止重复
        fileName = filePath + UUID.randomUUID() + fileName;
        // 文件对象
        File dest = new File(fileName);
        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }


    /***
     * 文件下载
     * @param response
     * @throws Exception
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response) throws Exception {
        // 文件地址，真实环境是存放在数据库中的
        File file = new File("/Users/apple/Documents/2.png");
        // 穿件输入对象
        FileInputStream fis = new FileInputStream(file);
        // 设置相关格式
        response.setContentType("application/force-download");
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" + "2.png");
        // 创建输出对象
        OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }

}