package com.example.exception;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/15 23:01
 *
 * 自定义加密超出字段长度异常类
 */


public class EncryptBeyondLengthException extends Exception{

    public EncryptBeyondLengthException() {

    }

    public EncryptBeyondLengthException(String message) {
        super(message);
    }
}
