package com.example.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.sun.org.apache.xpath.internal.operations.String;

import java.io.IOException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/4/17 18:10
 */
public class myconsumer extends DefaultConsumer {

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public myconsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleConsumeOk(java.lang.String consumerTag) {
        System.out.println("消费者编号"+consumerTag+"消费消息完成");
    }


    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//            if (envelope.getDeliveryTag() % 2 == 0) {
//                getChannel().basicAck(envelope.getDeliveryTag(),false);
//                System.out.println("消费已接受");
//            }
//
//            else {
//                getChannel().basicReject(envelope.getDeliveryTag(),true);
//                System.out.println("消费已拒绝");
//            }
        System.out.println(consumerTag);
    }
}
