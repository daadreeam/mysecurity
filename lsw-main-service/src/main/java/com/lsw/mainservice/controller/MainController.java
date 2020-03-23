package com.lsw.mainservice.controller;

import com.lsw.mainservice.feignclient.MsgFeignInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/main")
@Slf4j
public class MainController {

    @Autowired
    private AmqpTemplate amqpTemplate;


//    @Autowired
    @Resource
    private MsgFeignInterface msgFeignInterface;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test")
    public String test(){
        log.info("测试msg的test()feign接口");
        String msg = msgFeignInterface.test();
        return "main: " + msg;
    }

    @GetMapping("/testNoFeign")
    public String test2(){
        log.info("测试msg的test()no feign");
        String msg = restTemplate.getForObject("http://lsw-gateway/api/lsw-msg/msg/test", String.class);
        return "main: " + msg;
    }
    @GetMapping("/testSelf")
    public String testSelf(){
        log.info("直接调用测试自己");
        return "main: " + "直接调用测试自己";
    }

    @GetMapping("/code")
    public String getCode(){
        log.info("测试msg的getCode()feign接口");
        String msg = msgFeignInterface.getCode();
        return "main: " + msg;
    }

    @GetMapping("/sendMsg")
    public String sendQ(@RequestParam(value = "msg", required = false) String msg){

        amqpTemplate.convertAndSend("test.exchange","test.code","code生产者发过来的消息: " + msg);
        log.info("code 已发送消息: {}", msg);
        return "code消息已发送";
    }

    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam(value = "msg", required = false) String msg){

        amqpTemplate.convertAndSend("test.exchange","test.email","email生产者发过来的消息: " + msg);
        log.info("email 已发送消息: {}", msg);
        return "email消息已发送";
    }
    @GetMapping("/order")
    public String sendOrder(@RequestParam(value = "msg", required = false) String msg){

        amqpTemplate.convertAndSend("test.exchange","test.order","order生产者发过来的消息: " + msg);
        log.info("order 已发送消息: {}", msg);
        return "order消息已发送";
    }
}
