/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.greenstock.database;

/**
 *
 * @author blackheart
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

   public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed() || !connection.isValid(2)) { // Ensure connection is valid
        String url = "jdbc:mysql://localhost:3306/greenstock";
        String user = "archange";
        String password = "goodboy525";
        connection = DriverManager.getConnection(url, user, password);
    }
    return connection;
}


    // public static void closeConnection() {
    //     if (connection != null) {
    //         try {
    //             connection.close();
    //         } catch (SQLException e) {
    //             System.err.println("Failed to close connection: " + e.getMessage());
    //         }
    //     }
    // }

    public static void testConnection() throws SQLException {
        getConnection(); // Ensure connection is initialized
        System.out.println("Database connection test successful!");
    }
}
