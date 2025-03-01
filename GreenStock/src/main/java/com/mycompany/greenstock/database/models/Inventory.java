package com.mycompany.greenstock.database.models;

public class Inventory {
    private int id;
    private int plantId;
    private int nurseryId;
    private int quantity;
    private String potSize;
    private double price;

    // Constructors, getters, and setters
    public Inventory() {}

    public Inventory(int id, int plantId, int nurseryId, int quantity, 
                    String potSize, double price) {
        this.id = id;
        this.plantId = plantId;
        this.nurseryId = nurseryId;
        this.quantity = quantity;
        this.potSize = potSize;
        this.price = price;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPlantId() { return plantId; }
    public void setPlantId(int plantId) { this.plantId = plantId; }
    public int getNurseryId() { return nurseryId; }
    public void setNurseryId(int nurseryId) { this.nurseryId = nurseryId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getPotSize() { return potSize; }
    public void setPotSize(String potSize) { this.potSize = potSize; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
} 