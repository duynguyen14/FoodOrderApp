package com.example.myapplication.models;

public class HomeHorModel {
    int image;
    String name;

    int id;

    public HomeHorModel(int image, String name,int id) {
        this.image = image;
        this.name = name;
        this.id = id;
    }

    public HomeHorModel() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
