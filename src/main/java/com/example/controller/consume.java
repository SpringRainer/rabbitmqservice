package com.example.controller;

import com.example.consumer.rabbitmq_consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/4/17 22:07
 */
@Controller
public class consume {
    @Autowired
    private rabbitmq_consumer rabbitmq_consumer;

    @RequestMapping("/consume")
    @ResponseBody
    public String consume() throws IOException, TimeoutException {
        for (int i =0; i<10; i++) {
            rabbitmq_consumer.consume();
        }
        return "success";
    }
}
