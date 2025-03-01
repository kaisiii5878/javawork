package com.mycompany.greenstock.database.daos;

import com.mycompany.greenstock.database.BaseDAO;
import com.mycompany.greenstock.database.models.Nursery;
import com.mycompany.greenstock.database.DatabaseConnection;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NurseryDAO extends BaseDAO<Nursery> {

    @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected String getTableName() {
        return "nurseries";
    }

    @Override
    protected Nursery mapResultSetToEntity(ResultSet rs) throws SQLException {
        Nursery nursery = new Nursery();
        nursery.setId(rs.getInt("id"));
        nursery.setName(rs.getString("name"));
        nursery.setLocation(rs.getString("location"));
        nursery.setContactPhone(rs.getString("contact_phone"));
        nursery.setEmail(rs.getString("email"));
        nursery.setWebsite(rs.getString("website"));
        return nursery;
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, Nursery nursery) throws SQLException {
        stmt.setString(1, nursery.getName());
        stmt.setString(2, nursery.getLocation());
        stmt.setString(3, nursery.getContactPhone());
        stmt.setString(4, nursery.getEmail());
        stmt.setString(5, nursery.getWebsite());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Nursery nursery) throws SQLException {
        stmt.setString(1, nursery.getName());
        stmt.setString(2, nursery.getLocation());
        stmt.setString(3, nursery.getContactPhone());
        stmt.setString(4, nursery.getEmail());
        stmt.setString(5, nursery.getWebsite());
        stmt.setInt(6, nursery.getId());
    }

    @Override
    protected int getParameterCount(Nursery nursery) {
        return 5;
    }

    @Override
    protected String getColumnNames(Nursery nursery) {
        return "name, location, contact_phone, email, website";
    }

    // CRUD Operations
    public void create(Nursery nursery) throws SQLException {
        String query = "INSERT INTO " + getTableName() + 
            " (name, location, contact_phone, email, website) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setCreateParameters(stmt, nursery);
            stmt.executeUpdate();
        }
    }

    public Nursery read(int id) throws SQLException {
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

    public void update(Nursery nursery) throws SQLException {
        String query = "UPDATE " + getTableName() + 
            " SET name = ?, location = ?, contact_phone = ?, email = ?, website = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, nursery);
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

    public List<Nursery> getAll() throws SQLException {
        List<Nursery> nurseries = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                nurseries.add(mapResultSetToEntity(rs));
            }
        }
        return nurseries;
    }
} 