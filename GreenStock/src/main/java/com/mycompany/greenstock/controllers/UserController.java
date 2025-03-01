package com.mycompany.greenstock.controllers;

import com.mycompany.greenstock.services.UserService;
import com.mycompany.greenstock.database.models.User;

public class UserController {
    private final UserService userService = new UserService();

    public String createUser(User user) {
        try {
            userService.createUser(user);
            return "User created successfully";
        } catch (Exception e) {
            return "Error creating user: " + e.getMessage();
        }
    }

    public User authenticateUser(String username, String password) {
        try {
            return userService.authenticateUser(username, password);
        } catch (Exception e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            return null;
        }
    }

    public String updateUser(User user) {
        try {
            userService.updateUser(user);
            return "User updated successfully";
        } catch (Exception e) {
            return "Error updating user: " + e.getMessage();
        }
    }

    public String deleteUser(int id) {
        try {
            userService.deleteUser(id);
            return "User deleted successfully";
        } catch (Exception e) {
            return "Error deleting user: " + e.getMessage();
        }
    }

    public User getUser(int id) {
        try {
            return userService.getUserById(id);
        } catch (Exception e) {
            System.err.println("Error getting user: " + e.getMessage());
            return null;
        }
    }
} 