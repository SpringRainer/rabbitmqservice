package com.example.exception;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/28 23:53
 * 消费异常
 */
public class ConsumeException extends Exception{

    public ConsumeException() {

    }

    public ConsumeException(String message) {
        super(message);
    }
}
