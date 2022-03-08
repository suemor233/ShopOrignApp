package com.example.xch.scanzxing.entity;

public class GoodData {
    private String GoodName;

    public GoodData(String goodName) {
        GoodName = goodName;
    }

    public String getGoodName() {
        return GoodName;
    }

    public void setGoodName(String goodName) {
        GoodName = goodName;
    }

    @Override
    public String toString() {
        return "GoodData{" +
                "GoodName='" + GoodName + '\'' +
                '}';
    }
}
