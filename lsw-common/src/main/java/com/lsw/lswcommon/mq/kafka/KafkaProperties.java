package com.lsw.lswcommon.mq.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties("spring.kafka.topic")
@Data
public class KafkaProperties implements Serializable {
    private String groupId;
    private String[] topicName;
}
