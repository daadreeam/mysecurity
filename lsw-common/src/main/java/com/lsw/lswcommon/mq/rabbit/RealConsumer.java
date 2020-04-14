package com.lsw.lswcommon.mq.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class RealConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("47.93.198.65");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHandshakeTimeout(1000);

        //2 获取Connection
        Connection connection = connectionFactory.newConnection();
        //3 通过Connection创建一个新的Channel
        Channel channel = connection.createChannel();

        // 死信
        String dlxExchangeName = "dlx.exchange";
        String dlxQueueName = "dlx.queue";
        // 普通交换机
        String normalExchangeName = "normal.exchange";
        String dlxRoutingKey = "dlx.save";
        String normalQueueName = "normal.queue";

        // 声明死信队列交换机
        HashMap<String, Object> argumentMap = new HashMap<>();
        argumentMap.put("x-dead-letter-exchange", dlxExchangeName);
        // 正常的队列声明和绑定
        channel.exchangeDeclare(normalExchangeName, BuiltinExchangeType.TOPIC.getType(),true);
        channel.queueDeclare(normalQueueName, true, false, false, argumentMap);
        channel.queueBind(normalQueueName, normalExchangeName, dlxRoutingKey);

        // 处理死信队列 和 死信交换机
        channel.exchangeDeclare(dlxExchangeName, BuiltinExchangeType.TOPIC.getType(),true, false, null);
        channel.queueDeclare(dlxQueueName, true, false, false, null);
        channel.queueBind(dlxQueueName, dlxExchangeName, "#"); // 所有的死信都往这个死信队列路由

        String s = channel.basicConsume(normalQueueName, true, new RabbitConsumer(channel));
        System.out.println("s = " + s);
//        channel.basicAck(); // 这里看看自己ack怎么弄
    }
}
