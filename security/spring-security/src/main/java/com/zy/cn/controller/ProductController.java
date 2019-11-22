package com.zy.cn.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/product")
public class ProductController {

//    首页
    @RequestMapping("/index")
    public String selectByPrimaryKey(HttpSession session){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null){
            if (principal instanceof UserDetails){
                UserDetails userDetails = (UserDetails)principal;
                session.setAttribute("username",userDetails.getUsername());
            }
        }
        return "index";
    }
    @RequestMapping("/add")
    public String add(){
        return "product/add";
    }
    @RequestMapping("/update")
    public String update(){
        return "product/update";
    }
    @RequestMapping("/list")
    public String list(){
        return "product/list";
    }
    @RequestMapping("/delete")
    public String delete(){
        return "product/delete";
    }

}