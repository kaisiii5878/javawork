package com.mycompany.greenstock.database.models;

public class Plant {
    private int id;
    private String scientificName;
    private String commonName;
    private String description;
    private String plantType;
    private String growthRate;
    private String matureSize;
    private String sunRequirements;
    private String waterRequirements;
    private String soilType;
    private String hardinessZone;

    // Constructors
    public Plant() {}

    public Plant(int id, String scientificName, String commonName, String description, 
                String plantType, String growthRate, String matureSize, String sunRequirements,
                String waterRequirements, String soilType, String hardinessZone) {
        this.id = id;
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.description = description;
        this.plantType = plantType;
        this.growthRate = growthRate;
        this.matureSize = matureSize;
        this.sunRequirements = sunRequirements;
        this.waterRequirements = waterRequirements;
        this.soilType = soilType;
        this.hardinessZone = hardinessZone;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public String getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(String growthRate) {
        this.growthRate = growthRate;
    }

    public String getMatureSize() {
        return matureSize;
    }

    public void setMatureSize(String matureSize) {
        this.matureSize = matureSize;
    }

    public String getSunRequirements() {
        return sunRequirements;
    }

    public void setSunRequirements(String sunRequirements) {
        this.sunRequirements = sunRequirements;
    }

    public String getWaterRequirements() {
        return waterRequirements;
    }

    public void setWaterRequirements(String waterRequirements) {
        this.waterRequirements = waterRequirements;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getHardinessZone() {
        return hardinessZone;
    }

    public void setHardinessZone(String hardinessZone) {
        this.hardinessZone = hardinessZone;
    }
} 