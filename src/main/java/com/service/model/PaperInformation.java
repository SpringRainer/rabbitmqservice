package com.service.model;

import java.io.Serializable;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/24 21:39
 *
 */
@Deprecated
public class PaperInformation implements Serializable {
    private String paperName;
    private String paperSubject;
    private String paperAddress;
    private byte[] paperResource;

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getPaperSubject() {
        return paperSubject;
    }

    public void setPaperSubject(String paperSubject) {
        this.paperSubject = paperSubject;
    }

    public String getPaperAddress() {
        return paperAddress;
    }

    public void setPaperAddress(String paperAddress) {
        this.paperAddress = paperAddress;
    }

    public byte[] getPaperResource() {
        return paperResource;
    }

    public void setPaperResource(byte[] paperResource) {
        this.paperResource = paperResource;
    }

    @Override
    public String toString() {
        return "PaperInformation{" +
                "paperName='" + paperName + '\'' +
                ", paperSubject='" + paperSubject + '\'' +
                ", paperAddress='" + paperAddress + '\'' +
                ", paperResource=" + "字节流" +
                '}'+'\n';
    }

}
