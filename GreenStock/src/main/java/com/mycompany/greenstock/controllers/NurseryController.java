package com.mycompany.greenstock.controllers;

import com.mycompany.greenstock.services.NurseryService;
import com.mycompany.greenstock.database.models.Nursery;
import java.util.List;

public class NurseryController {
    private final NurseryService nurseryService = new NurseryService();

    public String createNursery(Nursery nursery) {
        try {
            nurseryService.createNursery(nursery);
            return "Nursery created successfully";
        } catch (Exception e) {
            return "Error creating nursery: " + e.getMessage();
        }
    }

    public Nursery getNursery(int id) {
        try {
            return nurseryService.getNurseryById(id);
        } catch (Exception e) {
            System.err.println("Error getting nursery: " + e.getMessage());
            return null;
        }
    }

    public List<Nursery> getAllNurseries() {
        try {
            return nurseryService.getAllNurseries();
        } catch (Exception e) {
            System.err.println("Error getting nurseries: " + e.getMessage());
            return List.of();
        }
    }

    public String updateNursery(Nursery nursery) {
        try {
            nurseryService.updateNursery(nursery);
            return "Nursery updated successfully";
        } catch (Exception e) {
            return "Error updating nursery: " + e.getMessage();
        }
    }

    public String deleteNursery(int id) {
        try {
            nurseryService.deleteNursery(id);
            return "Nursery deleted successfully";
        } catch (Exception e) {
            return "Error deleting nursery: " + e.getMessage();
        }
    }
} 