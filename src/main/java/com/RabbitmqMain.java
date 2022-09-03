package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/14 23:45
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.service.dao")
@EnableRetry
public class RabbitmqMain {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqMain.class,args);
    }
}
