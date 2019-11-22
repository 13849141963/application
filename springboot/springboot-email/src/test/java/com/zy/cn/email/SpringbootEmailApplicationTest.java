package com.zy.cn.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootEmailApplication.class)
public class SpringbootEmailApplicationTest {

    /***
     * 测试通过使用时直接修改application.properties中的邮箱相关参数
     */

    @Autowired
    MailService mailService;

    @Autowired
    TemplateEngine templateEngine;

    @Test
    public void sendEasy() {
        mailService.sendSimpleMail("17310649163@163.com", "简单文本邮件", "这是我的第一封邮件,哈哈...");
    }


    /**
     * HTML邮件发送
     *
     * @throws Exception
     */
    @Test
    public void sendHtmlMailTest() throws Exception {

        String content = "<html>\n" +
                "<body>\n" +
                "<h1 style=\"color: red\"> hello world , 这是一封HTML邮件</h1>" +
                "</body>\n" +
                "</html>";


        mailService.sendHtmlMail("17310649163@163.com", "Html邮件发送", content);
    }

    /**
     * 发送副本邮件
     *
     * @throws Exception
     */
    @Test
    public void sendAttachmentMailTest() throws Exception {
        String filepath = "/Users/apple/Documents/阿里巴巴Java开发手册1.4.0.pdf";

        mailService.sendAttachmentMail("17310649163@163.com", "发送副本", "这是一篇带附件的邮件", filepath);

    }

    /**
     * 发送图片邮件
     *
     * @throws Exception
     */
    @Test
    public void sendImageMailTest() throws Exception {
        //发送多个图片的话可以定义多个 rscId,定义多个img标签

        String filePath = "/Users/apple/Documents/2.png";
        String rscId = "ligh001";
        String content = "<html><body> 这是有图片的邮件: <img src=\'cid:" + rscId + "\'> </img></body></html>";

        mailService.sendImageMail("17310649163@163.com", "这是一个带图片的邮件", content, filePath, rscId);
    }

    /**
     * 发送邮件模板
     *
     * @throws Exception
     */
    @Test
    public void sendTemplateEmailTest() throws Exception {
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("templates", context);
        mailService.sendHtmlMail("17310649163@163.com", "这是一个模板文件", emailContent);
    }
}