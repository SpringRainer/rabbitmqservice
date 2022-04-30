package com.example.consumer;

import com.example.factory.factory;
import com.rabbitmq.client.*;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/4/17 16:08
 */

@Component
public class rabbitmq_consumer {
    public void consume() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = factory.factory;
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        Scanner scanner = new Scanner(System.in);

        channel.basicConsume("queue_01",false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("当前消费的消息"+s);
                if (Integer.parseInt(s.substring(4)) % 2 == 0) {
                    getChannel().basicAck(envelope.getDeliveryTag(),false);
                    System.out.println("消费已接受"+envelope.getDeliveryTag());
                }
                else {
                    getChannel().basicReject(envelope.getDeliveryTag(),true);
                    System.out.println("消费已拒绝"+envelope.getDeliveryTag());
                }
            }
        });
//        connection.close();
    }
}
