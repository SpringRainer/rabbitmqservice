package com.example.listener;

import com.example.configuration.RabbitmqConfiguration;
import com.example.exception.SendToMessageQueueException;
import com.example.retry.RetryTemplate;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Return;
import com.rabbitmq.client.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/23 10:18
 */
@Component
public class ConfirmListenerConfig {

    private RetryTemplate retryTemplate;

    private RabbitmqConfiguration rabbitmqConfiguration;

    private Channel channel;

    @PostConstruct
    public void after() throws IOException, TimeoutException {
        this.channel = rabbitmqConfiguration.getChannel();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public RabbitmqConfiguration getRabbitmqConfiguration() {
        return rabbitmqConfiguration;
    }

    @Autowired
    public void setRabbitmqConfiguration(RabbitmqConfiguration rabbitmqConfiguration) {
        this.rabbitmqConfiguration = rabbitmqConfiguration;
    }

    public RetryTemplate getRetryTemplate() {
        return retryTemplate;
    }

    @Autowired
    public void setRetryTemplate(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    // 消费者监听
    public ConfirmListener createConsumerListener() {
        return new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息序列: "+deliveryTag +"消费者ack完成!");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息序列: "+deliveryTag +"消费者消费异常!");
            }
        };
    }

    // 生产者监听发送成功回调
    public ConfirmCallback createProducerAckListener() {
        return (deliveryTag, multiple) -> System.out.println("消息序列: "+deliveryTag+"\t发送到交换机成功! " +"\t"+multiple);
    }


    // 监听是否发送到交换机成功
    public ConfirmCallback createProducerNackListener() {
        return (deliveryTag, multiple) -> System.out.println("消息序列: "+deliveryTag+"\t未发送到交换机!");
    }

    // 监听是否发送到指定队列
    public ReturnCallback createReturnCallback() {
        return returnMessage -> {
            System.out.println("当前消息未发送到消息队列, 消息序列号是: "+ returnMessage.getReplyCode());

            String message = new String(returnMessage.getBody(), Charset.defaultCharset());

            try {
                getRetryTemplate().produceTemplate(message);
            } catch (IOException | SendToMessageQueueException e) {
                e.printStackTrace();
            }
        };
    }
}
