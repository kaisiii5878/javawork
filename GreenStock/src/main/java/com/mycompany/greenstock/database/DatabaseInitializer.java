package com.mycompany.greenstock.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    
    public static void initializeDatabase() {
        String[] createTableQueries = {
            // Add the users table at the beginning
            "CREATE TABLE IF NOT EXISTS users (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "username VARCHAR(255) NOT NULL UNIQUE, " +
            "password_hash VARCHAR(255) NOT NULL, " +
            "email VARCHAR(255) NOT NULL UNIQUE, " +
            "role ENUM('ADMIN', 'MANAGER', 'STAFF') NOT NULL, " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "last_login TIMESTAMP NULL)",

            // Create tables without foreign keys first
            "CREATE TABLE IF NOT EXISTS plants (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "scientific_name VARCHAR(255) NOT NULL, " +
            "common_name VARCHAR(255) NOT NULL, " +
            "description TEXT, " +
            "plant_type ENUM('TREE', 'SHRUB', 'FLOWER', 'SUCCULENT', 'HERB') NOT NULL, " +
            "growth_rate ENUM('SLOW', 'MODERATE', 'FAST') NOT NULL, " +
            "mature_size VARCHAR(100), " +
            "sun_requirements VARCHAR(100), " +
            "water_requirements VARCHAR(100), " +
            "soil_type VARCHAR(100), " +
            "hardiness_zone VARCHAR(10))",

            "CREATE TABLE IF NOT EXISTS nurseries (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL, " +
            "location VARCHAR(255) NOT NULL, " +
            "contact_phone VARCHAR(20), " +
            "email VARCHAR(255), " +
            "website VARCHAR(255))",

            "CREATE TABLE IF NOT EXISTS inventory (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "plant_id INT NOT NULL, " +
            "nursery_id INT NOT NULL, " +
            "quantity INT NOT NULL, " +
            "pot_size VARCHAR(50), " +
            "price DECIMAL(10,2) NOT NULL, " +
            "FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE CASCADE, " +
            "FOREIGN KEY (nursery_id) REFERENCES nurseries(id) ON DELETE CASCADE)",

            "CREATE TABLE IF NOT EXISTS customers (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL, " +
            "email VARCHAR(255) NOT NULL, " +
            "phone VARCHAR(20), " +
            "address TEXT)",

            "CREATE TABLE IF NOT EXISTS sales (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "customer_id INT NOT NULL, " +
            "sale_date DATETIME NOT NULL, " +
            "total_amount DECIMAL(10,2) NOT NULL, " +
            "FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE)",

            "CREATE TABLE IF NOT EXISTS sale_items (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "sale_id INT NOT NULL, " +
            "inventory_id INT NOT NULL, " +
            "quantity INT NOT NULL, " +
            "price DECIMAL(10,2) NOT NULL, " +
            "FOREIGN KEY (sale_id) REFERENCES sales(id) ON DELETE CASCADE, " +
            "FOREIGN KEY (inventory_id) REFERENCES inventory(id) ON DELETE CASCADE)"
        };

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Disable foreign key checks temporarily
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            
            for (String query : createTableQueries) {
                stmt.executeUpdate(query);
            }
            
            // Re-enable foreign key checks
            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
            
            System.out.println("Database tables initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
} 