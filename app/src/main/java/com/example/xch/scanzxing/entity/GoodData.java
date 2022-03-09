package com.example.xch.scanzxing.entity;

public class GoodData {
    private String GoodName;
    private String error;
    private String id;

    public GoodData(String goodName, String error, String id) {
        GoodName = goodName;
        this.error = error;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }



    public String getGoodName() {
        return GoodName;
    }

    public void setGoodName(String goodName) {
        GoodName = goodName;
    }


}
