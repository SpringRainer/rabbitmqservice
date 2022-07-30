package com.example.channelPool;

import com.example.channelPool.channelmap.ChannelConstant;
import com.example.channelPool.channelmap.ChannelMapper;
import com.example.configuration.RabbitmqConfiguration;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/23 13:13
 */
@DependsOn("rabbitmqConfiguration")
@Configuration
public class ChannelPoolUtil {

    private RabbitmqConfiguration rabbitmqConfiguration;

    public RabbitmqConfiguration getRabbitmqConfiguration() {
        return rabbitmqConfiguration;
    }

    @Autowired
    public void setRabbitmqConfiguration(RabbitmqConfiguration rabbitmqConfiguration) {
        this.rabbitmqConfiguration = rabbitmqConfiguration;
    }

    @Bean
    public ChannelMapper createStaticChannel() throws TimeoutException, IOException {
        ChannelMapper channelMapper = new ChannelMapper();
        Map<String, Channel> map = new LinkedHashMap<>();
        map.put(ChannelConstant.channelone, rabbitmqConfiguration.getChannel());
        map.put(ChannelConstant.channeltwo, rabbitmqConfiguration.getChannel());
        channelMapper.setChannelMap(map);
        return channelMapper;
    }
}
