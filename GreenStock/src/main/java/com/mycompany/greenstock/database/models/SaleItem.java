package com.mycompany.greenstock.database.models;

public class SaleItem {
    private int id;
    private int saleId;
    private int inventoryId;
    private int quantity;
    private double price;

    // Constructors, getters, and setters
    public SaleItem() {}

    public SaleItem(int id, int saleId, int inventoryId, int quantity, double price) {
        this.id = id;
        this.saleId = saleId;
        this.inventoryId = inventoryId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }
    public int getInventoryId() { return inventoryId; }
    public void setInventoryId(int inventoryId) { this.inventoryId = inventoryId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
} 