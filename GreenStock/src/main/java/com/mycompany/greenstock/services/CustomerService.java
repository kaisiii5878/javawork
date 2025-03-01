package com.mycompany.greenstock.services;

import com.mycompany.greenstock.database.daos.CustomerDAO;
import com.mycompany.greenstock.database.models.Customer;
import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public void createCustomer(Customer customer) throws SQLException {
        customerDAO.create(customer);
    }

    public Customer getCustomerById(int id) throws SQLException {
        return customerDAO.read(id);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAll();
    }

    public void updateCustomer(Customer customer) throws SQLException {
        customerDAO.update(customer);
    }

    public void deleteCustomer(int id) throws SQLException {
        customerDAO.delete(id);
    }
} 