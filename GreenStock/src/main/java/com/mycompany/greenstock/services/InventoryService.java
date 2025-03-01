package com.mycompany.greenstock.services;

import com.mycompany.greenstock.database.daos.InventoryDAO;
import com.mycompany.greenstock.database.models.Inventory;
import java.sql.SQLException;
import java.util.List;

public class InventoryService {
    private final InventoryDAO inventoryDAO;

    public InventoryService() {
        this.inventoryDAO = new InventoryDAO();
    }

    public void createInventory(Inventory inventory) throws SQLException {
        inventoryDAO.create(inventory);
    }

    public Inventory getInventoryById(int id) throws SQLException {
        return inventoryDAO.read(id);
    }

    public List<Inventory> getAllInventory() throws SQLException {
        return inventoryDAO.getAll();
    }

    public void updateInventory(Inventory inventory) throws SQLException {
        inventoryDAO.update(inventory);
    }

    public void deleteInventory(int id) throws SQLException {
        inventoryDAO.delete(id);
    }
} 