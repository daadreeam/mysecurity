package com.lsw.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestController {

    @Value("${foo}")
    private String foo;

    @GetMapping("/config/foo")
    public String testFoo(){
        System.out.println("foo值是 " + foo);
        return foo;
    }
}
