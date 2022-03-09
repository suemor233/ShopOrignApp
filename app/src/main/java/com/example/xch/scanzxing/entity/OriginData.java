package com.example.xch.scanzxing.entity;

public class OriginData {
    private String OriginName;
    private String OriginDesc;
    private String CreatedAt;
    private GoodData goods;

    public OriginData(String originName, String originDesc, String createdAt, GoodData goods) {
        OriginName = originName;
        OriginDesc = originDesc;
        CreatedAt = createdAt;
        this.goods = goods;
    }

    public String getOriginName() {
        return OriginName;
    }

    public void setOriginName(String originName) {
        OriginName = originName;
    }

    public String getOriginDesc() {
        return OriginDesc;
    }

    public void setOriginDesc(String originDesc) {
        OriginDesc = originDesc;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public GoodData getGoods() {
        return goods;
    }

    public void setGoods(GoodData goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "OriginData{" +
                "OriginName='" + OriginName + '\'' +
                ", OriginDesc='" + OriginDesc + '\'' +
                ", CreatedAt='" + CreatedAt + '\'' +
                ", goods=" + goods +
                '}';
    }
}
