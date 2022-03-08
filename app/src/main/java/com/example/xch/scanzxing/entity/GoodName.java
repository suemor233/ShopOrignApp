package com.example.xch.scanzxing.entity;

public class GoodName {
    private String errorCode;
    private String errorMessage;
    private String data;

    public GoodName(String errorCode, String errorMessage, String data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }


    public GoodName() {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "goodName{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
