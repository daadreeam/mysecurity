package com.lsw.msg.controller;

import com.lsw.msg.anno.AuthRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msg")
@Slf4j
public class MsgController {

    @GetMapping("/hello")
    @AuthRequired(authName = "admin")
    public String hello(){
        String s = "msg: 短信微服务hello";
        log.info(s);
        return s;
    }


    @GetMapping("/test")
    @AuthRequired(authName = "admin")
    public String test(){
        String s = "msg: 短信微服务的测试";
        log.info(s);
        return s;
    }

    @GetMapping("/code")
    public String getCode(){
        log.info("发送验证码 -》9999");
        return "msg: 9999";
    }
}
