package com.example.xch.scanzxing.entity;

import java.util.List;

public class GoodMessage {
    private String errorCode;
    private String errorMessage;
    private GoodData data;


    public GoodMessage(String errorCode, String errorMessage, GoodData data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public GoodMessage() {
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

    public GoodData getData() {
        return data;
    }

    public void setData(GoodData data) {
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
