package com.mycompany.greenstock.database.daos;

import com.mycompany.greenstock.database.BaseDAO;
import com.mycompany.greenstock.database.models.Inventory;
import com.mycompany.greenstock.database.DatabaseConnection;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InventoryDAO extends BaseDAO<Inventory> {

    @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected String getTableName() {
        return "inventory";
    }

    @Override
    protected Inventory mapResultSetToEntity(ResultSet rs) throws SQLException {
        Inventory inventory = new Inventory();
        inventory.setId(rs.getInt("id"));
        inventory.setPlantId(rs.getInt("plant_id"));
        inventory.setNurseryId(rs.getInt("nursery_id"));
        inventory.setQuantity(rs.getInt("quantity"));
        inventory.setPotSize(rs.getString("pot_size"));
        inventory.setPrice(rs.getDouble("price"));
        return inventory;
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, Inventory inventory) throws SQLException {
        stmt.setInt(1, inventory.getPlantId());
        stmt.setInt(2, inventory.getNurseryId());
        stmt.setInt(3, inventory.getQuantity());
        stmt.setString(4, inventory.getPotSize());
        stmt.setDouble(5, inventory.getPrice());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Inventory inventory) throws SQLException {
        stmt.setInt(1, inventory.getPlantId());
        stmt.setInt(2, inventory.getNurseryId());
        stmt.setInt(3, inventory.getQuantity());
        stmt.setString(4, inventory.getPotSize());
        stmt.setDouble(5, inventory.getPrice());
        stmt.setInt(6, inventory.getId());
    }

    @Override
    protected int getParameterCount(Inventory inventory) {
        return 5;
    }

    @Override
    protected String getColumnNames(Inventory inventory) {
        return "plant_id, nursery_id, quantity, pot_size, price";
    }

    // CRUD Operations
    public void create(Inventory inventory) throws SQLException {
        String query = "INSERT INTO " + getTableName() + 
            " (plant_id, nursery_id, quantity, pot_size, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setCreateParameters(stmt, inventory);
            stmt.executeUpdate();
        }
    }

    public Inventory read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        }
        return null;
    }

    public void update(Inventory inventory) throws SQLException {
        String query = "UPDATE " + getTableName() + 
            " SET plant_id = ?, nursery_id = ?, quantity = ?, pot_size = ?, price = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, inventory);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Inventory> getAll() throws SQLException {
        List<Inventory> inventoryList = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                inventoryList.add(mapResultSetToEntity(rs));
            }
        }
        return inventoryList;
    }
} 