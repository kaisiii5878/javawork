package com.mycompany.greenstock.services;

import com.mycompany.greenstock.database.daos.PlantDAO;
import com.mycompany.greenstock.database.models.Plant;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PlantService {
    private final PlantDAO plantDAO;

    public PlantService() {
        this.plantDAO = new PlantDAO();
    }

    public void createPlant(Plant plant) throws SQLException {
        try {
            // Add any business logic here (e.g., validation)
            plantDAO.create(plant);
        } catch (SQLException e) {
            throw new SQLException("Error in service: " + e.getMessage());
        }
    }

    public Plant getPlantById(int id) throws SQLException {
        return plantDAO.read(id);
    }

    public List<Plant> getAllPlants() throws SQLException {
        return plantDAO.getAll();
    }

    public void updatePlant(Plant plant) throws SQLException {
        // Add any business logic here
        plantDAO.update(plant);
    }

    public void deletePlant(int id) throws SQLException {
        // Add any business logic here
        plantDAO.delete(id);
    }

    // Additional business methods
    public List<Plant> searchPlantsByName(String name) throws SQLException {
        // Implement custom search logic
        return plantDAO.getAll().stream()
            .filter(p -> p.getCommonName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
    }
} 