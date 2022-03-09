package com.example.xch.scanzxing.entity;

public class GoodData {
    private String GoodName;
    private String brand;
    private String category;
    private String weight;
    private String storage;
    private String life;
    private boolean covid;
    private String CreatedAt;
    private String ShelfLife;
    private String ProductionDate;
    private String id;

    public GoodData(String goodName, String brand, String category, String weight, String storage, String life, boolean covid, String createdAt, String shelfLife, String productionDate, String id) {
        GoodName = goodName;
        this.brand = brand;
        this.category = category;
        this.weight = weight;
        this.storage = storage;
        this.life = life;
        this.covid = covid;
        CreatedAt = createdAt;
        ShelfLife = shelfLife;
        ProductionDate = productionDate;
        this.id = id;
    }

    public String getGoodName() {
        return GoodName;
    }

    public void setGoodName(String goodName) {
        GoodName = goodName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public boolean isCovid() {
        return covid;
    }

    public void setCovid(boolean covid) {
        this.covid = covid;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getShelfLife() {
        return ShelfLife;
    }

    public void setShelfLife(String shelfLife) {
        ShelfLife = shelfLife;
    }

    public String getProductionDate() {
        return ProductionDate;
    }

    public void setProductionDate(String productionDate) {
        ProductionDate = productionDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GoodData{" +
                "GoodName='" + GoodName + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", weight='" + weight + '\'' +
                ", storage='" + storage + '\'' +
                ", life='" + life + '\'' +
                ", covid=" + covid +
                ", CreatedAt='" + CreatedAt + '\'' +
                ", ShelfLife='" + ShelfLife + '\'' +
                ", ProductionDate='" + ProductionDate + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
