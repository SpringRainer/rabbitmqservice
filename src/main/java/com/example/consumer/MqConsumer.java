package com.example.consumer;

import com.example.configuration.RabbitmqConfiguration;
import com.example.constant.MqConstant;
import com.example.listener.ConfirmListenerConfig;
import com.example.redis.RedisForDuplicateConsume;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.service.dao.CrudForConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/19 23:41
 * 编程式开发 非注解开发
 */
@Component
public class MqConsumer {

    private RabbitmqConfiguration rabbitmqConfiguration;

    private ConfirmListenerConfig confirmListenerConfig;

    private CrudForConsumer crudForConsumer;

    private RedisForDuplicateConsume redisForDuplicateConsume;

    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisForDuplicateConsume getRedisForDuplicateConsume() {
        return redisForDuplicateConsume;
    }

    @Autowired
    public void setRedisForDuplicateConsume(RedisForDuplicateConsume redisForDuplicateConsume) {
        this.redisForDuplicateConsume = redisForDuplicateConsume;
    }

    @Autowired
    public void setRabbitmqConfiguration(RabbitmqConfiguration rabbitmqConfiguration) {
        this.rabbitmqConfiguration = rabbitmqConfiguration;
    }

    public RabbitmqConfiguration getRabbitmqConfiguration() {
        return rabbitmqConfiguration;
    }

    @Autowired
    public void setConfirmListenerConfig(ConfirmListenerConfig confirmListenerConfig) {
        this.confirmListenerConfig = confirmListenerConfig;
    }

    public ConfirmListenerConfig getConfirmListenerConfig() {
        return confirmListenerConfig;
    }

    public CrudForConsumer getCrudForConsumer() {
        return crudForConsumer;
    }

    @Autowired(required = false)
    public void setCrudForConsumer(CrudForConsumer crudForConsumer) {
        this.crudForConsumer = crudForConsumer;
    }

    /**
     * 定义消费方法 consume
     *
     * */
    public void consume() throws IOException, TimeoutException {
        // 创建一个虚拟信道
        Channel channel = rabbitmqConfiguration.getChannel();
        channel.addConfirmListener(confirmListenerConfig.createConsumerListener());
        channel.confirmSelect();

        // 添加消费成功的监听器
        channel.addConfirmListener(confirmListenerConfig.createConsumerListener());

        channel.basicConsume(MqConstant.QUEUE_ONE, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("当前消费者编号: "+consumerTag+"\t当前消息序列号"+envelope.getDeliveryTag() + "\t消息内容: "+new String(body, Charset.defaultCharset()));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (envelope.getDeliveryTag() == 3) {
                    try {
                        throw new Exception("消费网络终止! ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // 防止重复消费
                if (!getRedisForDuplicateConsume().existER(consumerTag, String.valueOf(envelope.getDeliveryTag()))) {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    crudForConsumer.insertInformation(new String(body, Charset.defaultCharset()));
                    System.out.println("当前消息消费完成");
                    getRedisTemplate().opsForList().leftPush(consumerTag, String.valueOf(envelope.getDeliveryTag()));
                }

                else {
                    System.out.println("重复消费");
                }
            }
        });
    }
}
