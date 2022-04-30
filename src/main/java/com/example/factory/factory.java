package com.example.factory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/4/17 16:10
 */

@Component
public class factory {
    public static ConnectionFactory factory = null;

    static {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("hello");
        factory = connectionFactory;
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        Channel channel = null;
        try {
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            channel.queueDeclare("queue_01",true,false,false,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            channel.exchangeDeclare("exchange_01","direct",true,false,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            channel.queueBind("queue_01","exchange_01","hello",null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("初始化完成");
    }

    public void init() {
        System.out.println("初始化成功");
    }
}
