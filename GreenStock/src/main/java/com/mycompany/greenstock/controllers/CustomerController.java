package com.mycompany.greenstock.controllers;

import com.mycompany.greenstock.services.CustomerService;
import com.mycompany.greenstock.database.models.Customer;
import java.util.List;

public class CustomerController {
    private final CustomerService customerService = new CustomerService();

    public String createCustomer(Customer customer) {
        try {
            customerService.createCustomer(customer);
            return "Customer created successfully";
        } catch (Exception e) {
            return "Error creating customer: " + e.getMessage();
        }
    }

    public Customer getCustomer(int id) {
        try {
            return customerService.getCustomerById(id);
        } catch (Exception e) {
            System.err.println("Error getting customer: " + e.getMessage());
            return null;
        }
    }

    public List<Customer> getAllCustomers() {
        try {
            return customerService.getAllCustomers();
        } catch (Exception e) {
            System.err.println("Error getting customers: " + e.getMessage());
            return List.of();
        }
    }

    public String updateCustomer(Customer customer) {
        try {
            customerService.updateCustomer(customer);
            return "Customer updated successfully";
        } catch (Exception e) {
            return "Error updating customer: " + e.getMessage();
        }
    }

    public String deleteCustomer(int id) {
        try {
            customerService.deleteCustomer(id);
            return "Customer deleted successfully";
        } catch (Exception e) {
            return "Error deleting customer: " + e.getMessage();
        }
    }
} 