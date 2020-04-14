package com.lsw.lswcommon.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KafkaController {
    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;

    @GetMapping("/kafka/send/{topic}/{msg}")
    public void send(@PathVariable("topic")String topic, @PathVariable("msg")String msg){
        ListenableFuture<SendResult<Object, Object>> future = kafkaTemplate.send(topic, msg);
        // 可以增加回调
        future.addCallback(
                new SuccessCallback<SendResult<Object, Object>>() {
                               @Override
                               public void onSuccess(SendResult<Object, Object> objectObjectSendResult) {
                                   log.info("生产者发送消息成功，SendResult<Object, Object> {}", objectObjectSendResult.toString());
                               }
                           },
                new FailureCallback() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        log.error("生产者发送消息失败 {}",throwable.getMessage());
                        // 异常就自己重试
                        send(topic, msg);
                    }
                }
        );
    }
}
