package com.example.controller.messagequeue;

import com.example.consumer.MqConsumer;
import com.example.exception.SendToMessageQueueException;
import com.example.producer.MqProducer;
import com.example.retry.RetryTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/30 12:59
 */

@Controller
public class test {

    private RetryTemplate retryTemplate;

    private MqProducer mqProducer;

    private MqConsumer mqConsumer;

    public MqConsumer getMqConsumer() {
        return mqConsumer;
    }

    @Autowired
    public void setMqConsumer(MqConsumer mqConsumer) {
        this.mqConsumer = mqConsumer;
    }

    private static final String SUCCESSFUL = "SUCCESS";

    public RetryTemplate getRetryTemplate() {
        return retryTemplate;
    }

    @Autowired
    public void setRetryTemplate(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    public MqProducer getMqProducer() {
        return mqProducer;
    }

    @Autowired
    public void setMqProducer(MqProducer mqProducer) {
        this.mqProducer = mqProducer;
    }

    @RequestMapping(value = "/produce")
    @ResponseBody
    public String produce() throws Exception {
        getMqProducer().produce();
        return SUCCESSFUL;
    }

    @RequestMapping(value = "/consume")
    @ResponseBody
    public String consume() throws Exception {
        getMqConsumer().consume();
        return SUCCESSFUL;
    }
}
