package com.example.myapplication.models;

public class HomeVerModel {
    int id;
    int image;
    String name;
    String time;
    String rating;
    String price;
    String quantity;

    public HomeVerModel() {
    }

    public HomeVerModel(int id,int image, String name, String time, String rating, String price, String quantity) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.time = time;
        this.rating = rating;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
