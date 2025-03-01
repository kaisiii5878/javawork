package com.mycompany.greenstock.database.models;

public class Nursery {
    private int id;
    private String name;
    private String location;
    private String contactPhone;
    private String email;
    private String website;

    // Constructors, getters, and setters
    public Nursery() {}

    public Nursery(int id, String name, String location, String contactPhone, 
                  String email, String website) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contactPhone = contactPhone;
        this.email = email;
        this.website = website;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
} 