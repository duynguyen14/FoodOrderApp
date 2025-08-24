package com.example.myapplication.models;

public class CartItem {
    private int id;
    private int foodId;
    private String name;
    private String price;
    private int imageRes;
    private int quantity;

    public CartItem(int id,int foodId, String name, String price, int imageRes, int quantity) {
        this.id = id;
        this.foodId =foodId;
        this.name = name;
        this.price = price;
        this.imageRes = imageRes;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPrice() { return price; }
    public int getImageRes() { return imageRes; }
    public int getQuantity() { return quantity; }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }
}
