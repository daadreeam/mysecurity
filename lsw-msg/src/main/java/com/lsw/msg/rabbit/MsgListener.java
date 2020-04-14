package com.lsw.msg.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MsgListener {

    // 消息日志，接受所有的test的消息
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "test.queue.all",durable = "true"),
                    exchange = @Exchange(value = "test.exchange",durable = "true",type = "topic"),
                    key = {"test.#"}
            )
    })
    public void sendMsg(String msg){
        String x = "all 接受到的msg为：" + msg;
        log.info(x);
//        System.out.println(x);

    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "test.queue.msg",durable = "true"),
                    exchange = @Exchange(value = "test.exchange",durable = "true",type = "topic"),
                    key = {"test.email","test.order"}
            )
    })
    public void sendEmail(String msg){
        String s = "email 接受到的msg为：" + msg + " 发送email通知";
        log.info(s);
//        System.out.println(s);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "test.queue.email",durable = "true"),
                    exchange = @Exchange(value = "test.exchange",durable = "true",type = "topic"),
                    key = {"test.code","test.order"}
            )
    })
    public void sendMsgCode(String msg){
        String s = "msgcode 接受到的msg为：" + msg + " 发送短信验证码通知";
        log.info(s);
//        System.out.println(s);
    }


}
