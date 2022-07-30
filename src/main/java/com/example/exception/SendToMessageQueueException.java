package com.example.exception;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/28 23:37
 * 发送端发生异常
 */
public class SendToMessageQueueException extends Exception{

    public SendToMessageQueueException() {

    }

    public SendToMessageQueueException(String message) {
        super(message);
    }
}
