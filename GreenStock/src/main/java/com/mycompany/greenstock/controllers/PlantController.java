package com.mycompany.greenstock.controllers;

import com.mycompany.greenstock.services.PlantService;
import com.mycompany.greenstock.database.models.Plant;
import java.util.List;

public class PlantController {
    private final PlantService plantService = new PlantService();

    public String createPlant(Plant plant) {
        try {
            plantService.createPlant(plant);
            return "Plant created successfully";
        } catch (Exception e) {
            return "Error in controller: " + e.getMessage();
        }
    }

    public Plant getPlant(int id) {
        try {
            return plantService.getPlantById(id);
        } catch (Exception e) {
            System.err.println("Error getting plant: " + e.getMessage());
            return null;
        }
    }

    public List<Plant> getAllPlants() {
        try {
            return plantService.getAllPlants();
        } catch (Exception e) {
            System.err.println("Error getting plants: " + e.getMessage());
            return List.of();
        }
    }

    public String deletePlant(int id) {
        try {
            plantService.deletePlant(id);
            return "Nursery deleted successfully";
        } catch (Exception e) {
            return "Error deleting nursery: " + e.getMessage();
        }
    }
    

    public String updatePlant(Plant plant) {
        try {
            plantService.updatePlant(plant);
            return "Sale updated successfully";
        } catch (Exception e) {
            return "Error updating sale: " + e.getMessage();
        }
    }
} 