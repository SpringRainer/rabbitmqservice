package com.example.configuration;

import com.example.channelPool.ChannelPoolUtil;
import com.example.channelPool.channelmap.ChannelConstant;
import com.example.constant.MqConstant;
import com.example.redis.OperationForExchangeRouteKey;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/23 12:21
 * 初始化交换机和队列
 */
@Configuration
//@EnableCaching
public class InitializeQueueAndExchange {

    private static ChannelPoolUtil channelPoolUtil;

    private Channel channel;

    private RedisTemplate<String, Object> redisTemplate;

    private OperationForExchangeRouteKey operationForExchangeRouteKey;

    public OperationForExchangeRouteKey getOperationForExchangeRouteKey() {
        return operationForExchangeRouteKey;
    }

    @Autowired
    public void setOperationForExchangeRouteKey(OperationForExchangeRouteKey operationForExchangeRouteKey) {
        this.operationForExchangeRouteKey = operationForExchangeRouteKey;
    }

    @PostConstruct
    public void init() throws TimeoutException, IOException {
        this.channel = channelPoolUtil.createStaticChannel().getChannelMap().get(ChannelConstant.channelone);
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }


    @Resource(name = "rabbitmqRedisTemplate")
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setChannelPoolUtil(ChannelPoolUtil channelPoolUtil) {
        InitializeQueueAndExchange.channelPoolUtil = channelPoolUtil;
    }

    public ChannelPoolUtil getChannelPoolUtil() {
        return channelPoolUtil;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * 初始化队列
     * @return queue
     */
    @Bean
    public String CreateQueue() throws IOException {
        // 设置持久化 共享队列 自动删除
        System.out.println("create queue!");
        channel.queueDeclare(MqConstant.QUEUE_ONE, true, false, true, null);
        channel.queueDeclare(MqConstant.QUEUE_TWO, true, false, true, null);
        return MqConstant.QUEUE_ONE;
    }

    /**
     * 初始化交换机
     */
    @Bean
    public String createExchange() throws IOException, TimeoutException {
        // 设置交换机 路由模式是direct 且持久化
        System.out.println("create exchanger!");

        channel.exchangeDeclare(MqConstant.EXCHANGE_ONE, "direct", true);
        return MqConstant.EXCHANGE_ONE;
    }

    /**
     * 绑定交换机和自由队列
     */
    @Bean
    public String createBind() throws IOException {
        System.out.println("bind successfully!");
        if (!getOperationForExchangeRouteKey().existER(MqConstant.EXCHANGE_ONE, MqConstant.ROUTE_KEY)) {
            getRedisTemplate().opsForList().leftPush(MqConstant.EXCHANGE_ONE, MqConstant.ROUTE_KEY);
        }

        if (!getOperationForExchangeRouteKey().existER(MqConstant.EXCHANGE_ONE, MqConstant.EXCHANGE_ROUTE_ABANDON)) {
            getRedisTemplate().opsForList().leftPush(MqConstant.EXCHANGE_ONE, MqConstant.EXCHANGE_ROUTE_ABANDON);
        }

        // 绑定交换机和队列 并设置路由键
        channel.queueBind(MqConstant.QUEUE_ONE, MqConstant.EXCHANGE_ONE, MqConstant.ROUTE_KEY);
        channel.queueBind(MqConstant.QUEUE_TWO, MqConstant.EXCHANGE_ONE, MqConstant.EXCHANGE_ROUTE_ABANDON);
        return MqConstant.EXCHANGE_ONE;
    }
}
