package com.example.producer;

import com.example.configuration.RabbitmqConfiguration;
import com.example.constant.MqConstant;
import com.example.listener.ConfirmListenerConfig;
import com.example.utils.Formater;
import com.rabbitmq.client.Channel;
import com.service.dao.CrudForMessageQueue;
import com.service.model.MessageTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/23 14:53
 */

@Component
public class MqProducer {

    private RabbitmqConfiguration rabbitmqConfiguration;

    private ConfirmListenerConfig confirmListenerConfig;

    private CrudForMessageQueue crudForMessageQueue;


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

    public CrudForMessageQueue getCrudForMessageQueue() {
        return crudForMessageQueue;
    }

    @Autowired(required = false)
    public void setCrudForMessageQueue(CrudForMessageQueue crudForMessageQueue) {
        this.crudForMessageQueue = crudForMessageQueue;
    }

    /**
     * 定义生产消息方法 produce
     *
     * */
    public void produce() throws Exception {
        // 创建一个虚拟信道
        Channel channel = rabbitmqConfiguration.getChannel();
        channel.confirmSelect();

        // 添加监听是否发送到交换机
        channel.addConfirmListener(confirmListenerConfig.createProducerAckListener(), confirmListenerConfig.createProducerNackListener());

        // 添加监听是否发送到队列
        channel.addReturnListener(confirmListenerConfig.createReturnCallback());

        List<MessageTemp> messageTemps = crudForMessageQueue.queryAllInformation();

        for (MessageTemp messageTemp : messageTemps) {

            if (messageTemp.getSeq().equals("201")) {
                System.out.println("当前消息已发出!!");
                channel.basicPublish(MqConstant.EXCHANGE_ONE, "MqConstant.routekey", true, null, messageTemp.getMessageContent().getBytes(Charset.defaultCharset()));
            }

            else {
                if (channel.waitForConfirms()) {
                    channel.basicPublish(MqConstant.EXCHANGE_ONE, MqConstant.ROUTE_KEY, true, null, messageTemp.getMessageContent().getBytes(Charset.defaultCharset()));
                    System.out.println("当前消息已发出!!");
                }
            }
        }
    }

    @Cacheable(cacheNames = "messageTemplate")
    public MessageTemp setMessageTemp(String seq, String messageContent) {
        MessageTemp messageTemp = new MessageTemp();
        messageTemp.setSeq(seq);
        messageTemp.setMessageContent(messageContent);
        messageTemp.setActiveTime(Formater.transferDateFormat());
        return messageTemp;
    }
}
