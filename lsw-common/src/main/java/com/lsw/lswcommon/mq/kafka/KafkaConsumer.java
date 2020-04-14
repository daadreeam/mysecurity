package com.lsw.lswcommon.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"test"}, groupId = "g1")
    public void consumer(ConsumerRecord<Object,Object> record){
        log.info("kafka 消费者 start");
        log.info("kafka 消费者消息, topic = {}, msg = {}", record.topic(), record.value());

        // do something ...

        log.info("kafka 消费者 end");

    }
}
