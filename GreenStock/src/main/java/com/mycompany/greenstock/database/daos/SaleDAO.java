package com.mycompany.greenstock.database.daos;

import com.mycompany.greenstock.database.BaseDAO;
import com.mycompany.greenstock.database.models.Sale;
import com.mycompany.greenstock.database.DatabaseConnection;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SaleDAO extends BaseDAO<Sale> {

    @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected String getTableName() {
        return "sales";
    }

    @Override
    protected Sale mapResultSetToEntity(ResultSet rs) throws SQLException {
        Sale sale = new Sale();
        sale.setId(rs.getInt("id"));
        sale.setCustomerId(rs.getInt("customer_id"));
        sale.setSaleDate(rs.getTimestamp("sale_date"));
        sale.setTotalAmount(rs.getDouble("total_amount"));
        return sale;
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, Sale sale) throws SQLException {
        stmt.setInt(1, sale.getCustomerId());
        stmt.setTimestamp(2, sale.getSaleDate());
        stmt.setDouble(3, sale.getTotalAmount());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Sale sale) throws SQLException {
        stmt.setInt(1, sale.getCustomerId());
        stmt.setTimestamp(2, sale.getSaleDate());
        stmt.setDouble(3, sale.getTotalAmount());
        stmt.setInt(4, sale.getId());
    }

    @Override
    protected int getParameterCount(Sale sale) {
        return 3;
    }

    @Override
    protected String getColumnNames(Sale sale) {
        return "customer_id, sale_date, total_amount";
    }

    // CRUD Operations
    public void create(Sale sale) throws SQLException {
        String query = "INSERT INTO " + getTableName() + 
            " (customer_id, sale_date, total_amount) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setCreateParameters(stmt, sale);
            stmt.executeUpdate();
        }
    }

    public Sale read(int id) throws SQLException {
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

    public void update(Sale sale) throws SQLException {
        String query = "UPDATE " + getTableName() + 
            " SET customer_id = ?, sale_date = ?, total_amount = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, sale);
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

    public List<Sale> getAll() throws SQLException {
        List<Sale> sales = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sales.add(mapResultSetToEntity(rs));
            }
        }
        return sales;
    }
} 