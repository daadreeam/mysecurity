package com.lsw.msg.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/transac")
@RestController
@Slf4j
public class TransacController {

    @GetMapping("/test")
    public String trans(@RequestParam("id") String id){

        log.info("test 接受到的id是 {}, msg 方法开始", id);

        if (Long.valueOf(id) < 1) {
            log.error("id非法，需要进行回滚");
            throw new RuntimeException("id非法，需要进行回滚");
        }
        // do sth
        log.info("正在执行msg 方法的业务逻辑");

        return "success";

    }
}
