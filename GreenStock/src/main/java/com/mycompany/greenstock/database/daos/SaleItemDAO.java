package com.mycompany.greenstock.database.daos;

import com.mycompany.greenstock.database.BaseDAO;
import com.mycompany.greenstock.database.models.SaleItem;
import com.mycompany.greenstock.database.DatabaseConnection;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class SaleItemDAO extends BaseDAO<SaleItem> {

    @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected String getTableName() {
        return "sale_items";
    }

    @Override
    protected SaleItem mapResultSetToEntity(ResultSet rs) throws SQLException {
        SaleItem saleItem = new SaleItem();
        saleItem.setId(rs.getInt("id"));
        saleItem.setSaleId(rs.getInt("sale_id"));
        saleItem.setInventoryId(rs.getInt("inventory_id"));
        saleItem.setQuantity(rs.getInt("quantity"));
        saleItem.setPrice(rs.getDouble("price"));
        return saleItem;
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, SaleItem saleItem) throws SQLException {
        stmt.setInt(1, saleItem.getSaleId());
        stmt.setInt(2, saleItem.getInventoryId());
        stmt.setInt(3, saleItem.getQuantity());
        stmt.setDouble(4, saleItem.getPrice());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, SaleItem saleItem) throws SQLException {
        stmt.setInt(1, saleItem.getSaleId());
        stmt.setInt(2, saleItem.getInventoryId());
        stmt.setInt(3, saleItem.getQuantity());
        stmt.setDouble(4, saleItem.getPrice());
        stmt.setInt(5, saleItem.getId());
    }

    @Override
    protected int getParameterCount(SaleItem saleItem) {
        return 4;
    }

    @Override
    protected String getColumnNames(SaleItem saleItem) {
        return "sale_id, inventory_id, quantity, price";
    }

    // CRUD Operations
    public void create(SaleItem saleItem) throws SQLException {
        String query = "INSERT INTO " + getTableName() + 
            " (sale_id, inventory_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setCreateParameters(stmt, saleItem);
            stmt.executeUpdate();
        }
    }

    public SaleItem read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        }
        return null;
    }

    public void update(SaleItem saleItem) throws SQLException {
        String query = "UPDATE " + getTableName() + 
            " SET sale_id = ?, inventory_id = ?, quantity = ?, price = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, saleItem);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<SaleItem> getAll() throws SQLException {
        List<SaleItem> saleItems = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                saleItems.add(mapResultSetToEntity(rs));
            }
        }
        return saleItems;
    }

    // Additional methods
    public List<SaleItem> getItemsBySaleId(int saleId) throws SQLException {
        List<SaleItem> saleItems = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName() + " WHERE sale_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, saleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    saleItems.add(mapResultSetToEntity(rs));
                }
            }
        }
        return saleItems;
    }
} 