package com.lsw.mainservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/transac")
@RestController
public class MainTransacController {

    //todo bytetcc解决分布式事务

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test")
    public String trans(@RequestParam("id") String id) {
        String msgResult = restTemplate.getForObject("/lsw-msg/transac/test?id=" + id, String.class, (Object) null);

        if (Long.valueOf(id) > 100) {
            throw new RuntimeException("main id>100,参数非法要回滚");
        }
        return "main " + msgResult;

    }
}
