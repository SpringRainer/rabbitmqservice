package com.example.channelPool.channelmap;

import com.rabbitmq.client.Channel;

import java.util.Map;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/23 13:15
 * channel缓存do类
 */
public class ChannelMapper {
    private Map<String, Channel> channelMap;

    public Map<String, Channel> getChannelMap() {
        return channelMap;
    }

    public void setChannelMap(Map<String, Channel> channelMap) {
        this.channelMap = channelMap;
    }
}
