package com.lsw.lswcommon.mq.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DlxConsumer2 {
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

        String dlxQueueName = "dlx.queue";

//        channel.queueBind(dlxQueueName,)
        String s = channel.basicConsume(dlxQueueName, false, new RabbitConsumer(channel));
        System.out.println("死信消费者2在topic模式下消费到了消息 s = " + s);
    }
}
