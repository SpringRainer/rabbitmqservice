package com.example.retry;

import com.example.configuration.RabbitmqConfiguration;
import com.example.constant.MqConstant;
import com.example.exception.ConsumeException;
import com.example.exception.SendToMessageQueueException;
import com.example.redis.OperationForExchangeRouteKey;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/28 23:33
 * 服务器发送端 接收端重试机制
 */
@Service
public class RetryTemplate {

    private RabbitmqConfiguration rabbitmqConfiguration;

    private Channel channel;

    private RedisTemplate<String, Object> redisTemplate;

    private OperationForExchangeRouteKey operationForExchangeRouteKey;

    private final StringBuffer buffer = new StringBuffer();

    public OperationForExchangeRouteKey getOperationForExchangeRouteKey() {
        return operationForExchangeRouteKey;
    }

    @Autowired
    public void setOperationForExchangeRouteKey(OperationForExchangeRouteKey operationForExchangeRouteKey) {
        this.operationForExchangeRouteKey = operationForExchangeRouteKey;
    }

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

    @Retryable(maxAttempts = 3, value = {SendToMessageQueueException.class}, backoff = @Backoff(delay = 1000L, multiplier = 1.5))
    public void produceTemplate(String message) throws IOException, SendToMessageQueueException {
        System.out.println("尝试再次进行发送操作!!");

        buffer.append(message);

        if (!getOperationForExchangeRouteKey().existER(MqConstant.EXCHANGE_ONE, "MqConstant.routekey")) {
            throw new SendToMessageQueueException("由于某种原因, 消息未发送到消息队列!");
        }

        channel.basicPublish(MqConstant.EXCHANGE_ONE, "MqConstant.routekey", true, null, message.getBytes(Charset.defaultCharset()));
    }

    @Recover
    public void recover(SendToMessageQueueException e) throws IOException, SendToMessageQueueException {
        System.out.println("网络繁忙, 请稍后重试"+ e.getMessage());
        String message = buffer.toString();
        int length = buffer.length();
        buffer.delete(0, length);

        if (!getOperationForExchangeRouteKey().existER(MqConstant.EXCHANGE_ONE, MqConstant.EXCHANGE_ROUTE_ABANDON)) {
            throw new SendToMessageQueueException("未找到绑定关系");
        }

        channel.basicPublish(MqConstant.EXCHANGE_ONE, MqConstant.EXCHANGE_ROUTE_ABANDON, true, null, message.getBytes(Charset.defaultCharset()));
        System.out.println("消息发送到废弃队列!");
    }

    @Retryable(maxAttempts = 3, value = {ConsumeException.class}, backoff = @Backoff(delay = 1000, multiplier = 1))
    public void consumeTemplate() throws ConsumeException {
        System.out.println("消费异常");
        throw new ConsumeException("消费异常");
    }

    @Recover
    public void recoverForConsumer(ConsumeException e) throws ConsumeException {
        System.out.println("消费异常, 请稍后重试");
        throw new ConsumeException("消费异常");
    }
}
