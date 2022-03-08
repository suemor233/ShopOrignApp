package com.example.xch.scanzxing.entity;

public class OriginData {
    private String OriginName;
    private String OriginDesc;
    private String CreatedAt;

    public OriginData(String originName, String originDesc, String createdAt) {
        OriginName = originName;
        OriginDesc = originDesc;
        CreatedAt = createdAt;
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

    @Override
    public String toString() {
        return "OriginData{" +
                "OriginName='" + OriginName + '\'' +
                ", OriginDesc='" + OriginDesc + '\'' +
                ", CreatedAt='" + CreatedAt + '\'' +
                '}';
    }
}
