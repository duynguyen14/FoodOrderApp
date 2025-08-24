package com.example.myapplication.models;

public class OrderModel {
    private int id;
    private String orderCode;
    private String status;
    private String orderDate;
    private double total;

    public OrderModel(int id, String orderCode, String status, String orderDate, double total) {
        this.id = id;
        this.orderCode = orderCode;
        this.status = status;
        this.orderDate = orderDate;
        this.total = total;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
