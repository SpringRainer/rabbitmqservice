package com.example.producer;

import com.example.factory.factory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.smartcardio.SunPCSC;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/4/17 16:47
 */
@Component
public class rabbitmq_producer {
    public void produce(String message) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = factory.factory;
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish("exchange_01","hello",null,message.getBytes());
    }
}
