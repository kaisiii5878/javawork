package com.mycompany.greenstock.controllers;

import com.mycompany.greenstock.services.InventoryService;
import com.mycompany.greenstock.database.models.Inventory;
import java.util.List;

public class InventoryController {
    private final InventoryService inventoryService = new InventoryService();

    public String createInventory(Inventory inventory) {
        try {
            inventoryService.createInventory(inventory);
            return "Inventory created successfully";
        } catch (Exception e) {
            return "Error creating inventory: " + e.getMessage();
        }
    }

    public Inventory getInventory(int id) {
        try {
            return inventoryService.getInventoryById(id);
        } catch (Exception e) {
            System.err.println("Error getting inventory: " + e.getMessage());
            return null;
        }
    }

    public List<Inventory> getAllInventory() {
        try {
            return inventoryService.getAllInventory();
        } catch (Exception e) {
            System.err.println("Error getting inventory: " + e.getMessage());
            return List.of();
        }
    }

    public String updateInventory(Inventory inventory) {
        try {
            inventoryService.updateInventory(inventory);
            return "Inventory updated successfully";
        } catch (Exception e) {
            return "Error updating inventory: " + e.getMessage();
        }
    }

    public String deleteInventory(int id) {
        try {
            inventoryService.deleteInventory(id);
            return "Inventory deleted successfully";
        } catch (Exception e) {
            return "Error deleting inventory: " + e.getMessage();
        }
    }
} 