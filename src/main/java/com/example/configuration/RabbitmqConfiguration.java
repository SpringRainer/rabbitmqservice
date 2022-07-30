package com.example.configuration;

import com.example.cipher.RsaDecoder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/13 21:24
 */

@Configuration
@PropertySource("classpath:constant/rabbitFactory.properties")
public class RabbitmqConfiguration {

    // rsa加密解密工具类
    private RsaDecoder rsaDecoder;

    // 注入rsa加密解密bean
    @Autowired
    public void setRsaDecoder(RsaDecoder rsaDecoder) {
        this.rsaDecoder = rsaDecoder;
    }

    public RsaDecoder getRsaDecoder() {
        return rsaDecoder;
    }

    @Value("${app.username}")
    private String username;

    @Value("${app.password}")
    private String password;

    @Bean
    public ConnectionFactory getConnectionFactory() {

        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setUsername("guest");

        connectionFactory.setPassword("guest");

        connectionFactory.setVirtualHost("hello");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        System.out.println("创建工厂成功!");
        return connectionFactory;
    }

    @Bean
    // 获取virtual信道
    public Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        System.out.println("获取连接");
        return connection.createChannel();
    }

}
