package com.service.model;

import java.io.Serializable;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/23 22:42
 */
@Deprecated
public class Student implements Serializable {
    private String stuId;
    private String sexType;
    private String passPort;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getSexType() {
        return sexType;
    }

    public void setSexType(String sexType) {
        this.sexType = sexType;
    }

    public String getPassPort() {
        return passPort;
    }

    public void setPassPort(String passPort) {
        this.passPort = passPort;
    }

    @Override
    public String toString() {
        return "student{" +
                "stuId='" + stuId + '\'' +
                ", sexType='" + sexType + '\'' +
                ", passPort='" + passPort + '\'' +
                '}';
    }
}
