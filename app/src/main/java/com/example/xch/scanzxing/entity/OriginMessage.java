package com.example.xch.scanzxing.entity;

import java.util.List;

public class OriginMessage {
    private String errorCode;
    private String errorMessage;
    private List<OriginData> data;


    public OriginMessage(String errorCode, String errorMessage, List<OriginData> data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public OriginMessage() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<OriginData> getData() {
        return data;
    }

    public void setData(List<OriginData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OriginMessage{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
