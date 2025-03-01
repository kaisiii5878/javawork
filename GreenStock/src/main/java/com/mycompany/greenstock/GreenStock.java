/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.greenstock;
import com.mycompany.greenstock.database.DatabaseConnection;
import com.mycompany.greenstock.database.DatabaseInitializer;
import com.mycompany.greenstock.database.daos.*;
import com.mycompany.greenstock.database.models.*;
import java.sql.SQLException;
import java.util.Date;
import com.mycompany.greenstock.views.*;

/**
 *
 * @author blackheart
 */
public class GreenStock {

    public static void main(String[] args) {
        System.out.println("Initializing greenstock...");
        
        // Add a shutdown hook to close the connection
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing database connection...");
//            DatabaseConnection.closeConnection();
        }));

        try {
            MainView mainview = new MainView();
            mainview.setVisible(true);

            // Initialize database tables
            DatabaseInitializer.initializeDatabase();
            
            // Test database connection
            DatabaseConnection.testConnection();
            
        

            // Test data insertion
//            testDatabaseOperations(
//                materialDAO, 
//                supplierDAO, 
//                projectDAO, 
//                purchaseOrderDAO, 
//                stockMovementDAO, 
//                projectMaterialDAO
//            );
            
            System.out.println("Application started successfully!");
            
        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
