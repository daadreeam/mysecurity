package com.lsw.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/auth")
@Controller
public class AuthController {

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "auth: get方法测试";
    }

    @PostMapping("/token")
    @ResponseBody
    public String getJwtToken(String userName, HttpServletResponse response){
        // todo 签发token
        // todo 存入redis
        // todo 返回登陆状态
        String authToken = "token:adsfasdfasdfasdqewrqwer123jlksadjf";
        response.setHeader("authToken", authToken);
        System.out.println("获取token");
        response.setStatus(666);
        return "auth: " + authToken;
    }
}
