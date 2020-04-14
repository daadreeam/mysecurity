package com.lsw.lswcommon.mq.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.jms.JmsProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("47.93.198.65");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHandshakeTimeout(1000);

        //2 获取Connection
        Connection connection = connectionFactory.newConnection();
        //3 通过Connection创建一个新的Channel
        Channel channel = connection.createChannel();

        String normalExchangeName = "normal.exchange";
        String dlxRoutingKey = "dlx.save";
        String dlxQueueName = "dlx.queue";

        String msg = "Hello RabbitMQ 这是一个死信队列消息 DLX Message";

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(JmsProperties.DeliveryMode.PERSISTENT.getValue()) // 消息持久化
                .expiration("5000") // 设置过期时间
                .contentEncoding("UTF-8")
                .build();

        channel.basicPublish(normalExchangeName, dlxRoutingKey, properties, msg.getBytes());

        System.out.println("生产者 生产成功！");

        channel.close();
        connection.close();

    }
}
