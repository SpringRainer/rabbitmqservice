package com.service.model;

import java.io.Serializable;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/24 17:46
 * 消息队列 body载体类
 */
public class MessageTemp implements Serializable {
    // 序列号
    private String seq;

    // 消息体内容
    private String messageContent;

    // 消息生效时间
    private String activeTime;

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    @Override
    public String toString() {
        return "MessageTemp{" +
                "seq='" + seq + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", activeTime='" + activeTime + '\'' +
                '}' +'\n';
    }
}
