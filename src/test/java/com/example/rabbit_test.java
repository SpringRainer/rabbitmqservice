package com.example;

import com.example.consumer.rabbitmq_consumer;
import com.example.producer.rabbitmq_producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/4/17 17:50
 */
@SpringBootTest(classes = main_demo_8080.class)
@RunWith(SpringRunner.class)

public class rabbit_test {
    @Autowired
    private rabbitmq_producer rabbitmq_producer;

    @Autowired
    private rabbitmq_consumer rabbitmq_consumer;

    @Test
    public void test_01() throws InterruptedException, IOException, TimeoutException {
        for (int i = 0; i<1000; i++) {
            rabbitmq_producer.produce("你好世界"+i);
            System.out.println("生产者发送消息成功");
        }
    }

    @Test
    public void test_02() throws IOException, TimeoutException, InterruptedException {
        for (int i =0; i<10; i++)
            rabbitmq_consumer.consume();
    }
}
