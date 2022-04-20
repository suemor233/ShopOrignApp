package com.example.xch.scanzxing.entity;

public class User {
    private String username;
    private String id;
    private String password;
    private String category;
    private String location;
    private String name;
    private int weight;


    public User(String username, String id, String password, String category, String location, String name, int weight) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.category = category;
        this.location = location;
        this.name = name;
        this.weight = weight;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
