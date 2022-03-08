package com.example.xch.scanzxing.api;

public interface TtitCallback {
    //OkHttp接口
    void onSuccess(String res);
    void onFailure(Exception e);
}