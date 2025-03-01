package com.mycompany.greenstock.services;

import com.mycompany.greenstock.database.daos.UserDAO;
import com.mycompany.greenstock.database.models.User;
import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void createUser(User user) throws SQLException {
        userDAO.create(user);
    }

    public User getUserById(int id) throws SQLException {
        return userDAO.read(id);
    }

    public User authenticateUser(String username, String password) throws SQLException {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPasswordHash().equals(password)) {
            userDAO.updateLastLogin(user.getId());
            return user;
        }
        return null;
    }

    public void updateUser(User user) throws SQLException {
        userDAO.update(user);
    }

    public void deleteUser(int id) throws SQLException {
        userDAO.delete(id);
    }
} 