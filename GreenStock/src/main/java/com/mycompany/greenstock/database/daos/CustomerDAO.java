package com.mycompany.greenstock.database.daos;

import com.mycompany.greenstock.database.BaseDAO;
import com.mycompany.greenstock.database.DatabaseConnection;
import java.util.ArrayList;
import com.mycompany.greenstock.database.models.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class CustomerDAO extends BaseDAO<Customer> {

    @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected String getTableName() {
        return "customers";
    }

    @Override
    protected Customer mapResultSetToEntity(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        return customer;
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, Customer customer) throws SQLException {
        stmt.setString(1, customer.getName());
        stmt.setString(2, customer.getEmail());
        stmt.setString(3, customer.getPhone());
        stmt.setString(4, customer.getAddress());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Customer customer) throws SQLException {
        stmt.setString(1, customer.getName());
        stmt.setString(2, customer.getEmail());
        stmt.setString(3, customer.getPhone());
        stmt.setString(4, customer.getAddress());
        stmt.setInt(5, customer.getId());
    }

    @Override
    protected int getParameterCount(Customer customer) {
        return 4;
    }

    @Override
    protected String getColumnNames(Customer customer) {
        return "name, email, phone, address";
    }

    // CRUD Operations
    public void create(Customer customer) throws SQLException {
        String query = "INSERT INTO " + getTableName() + 
            " (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setCreateParameters(stmt, customer);
            stmt.executeUpdate();
        }
    }

    public Customer read(int id) throws SQLException {
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

    public void update(Customer customer) throws SQLException {
        String query = "UPDATE " + getTableName() + 
            " SET name = ?, email = ?, phone = ?, address = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, customer);
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

    public List<Customer> getAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                customers.add(mapResultSetToEntity(rs));
            }
        }
        return customers;
    }
} 