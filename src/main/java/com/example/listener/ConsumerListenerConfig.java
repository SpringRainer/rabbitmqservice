package com.example.listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/23 10:52
 */
public class ConsumerListenerConfig {
    public Consumer createConsumer() {
        return new Consumer() {
            @Override
            public void handleConsumeOk(String consumerTag) {
                System.out.println("消费序号: "+consumerTag+" 消费完成!");
            }

            @Override
            public void handleCancelOk(String consumerTag) {
                System.out.println("消息序号: "+consumerTag+" 取消完成!");
            }

            @Override
            public void handleCancel(String consumerTag) throws IOException {
                System.out.println("消费序号: "+consumerTag+"处理取消!");
            }

            @Override
            public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                System.out.println("消费序号: "+consumerTag+"连接失效!");
            }

            @Override
            public void handleRecoverOk(String consumerTag) {
                System.out.println("消费序号: "+consumerTag+"补偿功能生效");
            }

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费序号: "+consumerTag+"\t 队列消息序号"+envelope.getDeliveryTag());

            }
        };
    }

}
