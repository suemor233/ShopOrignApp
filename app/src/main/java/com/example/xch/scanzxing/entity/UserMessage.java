package com.example.xch.scanzxing.entity;

public class UserMessage {
    private String errorCode;
    private String errorMessage;
    private Boolean success;
    private User data;

    public UserMessage(String errorCode, String errorMessage, Boolean success, User data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.success = success;
        this.data = data;
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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
