package com.mycompany.greenstock.database.models;

import java.sql.Timestamp;

public class Sale {
    private int id;
    private int customerId;
    private Timestamp saleDate;
    private double totalAmount;

    // Constructors, getters, and setters
    public Sale() {}

    public Sale(int id, int customerId, Timestamp saleDate, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public Timestamp getSaleDate() { return saleDate; }
    public void setSaleDate(Timestamp saleDate) { this.saleDate = saleDate; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
} 