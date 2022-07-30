package com.service.model;

import java.io.Serializable;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/25 23:29
 * 同步生产者消息
 */
public class AsyncTable implements Serializable {
    private Integer seq;
    private String asyncContent;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getAsyncContent() {
        return asyncContent;
    }

    public void setAsyncContent(String asyncContent) {
        this.asyncContent = asyncContent;
    }
}
