package com.mycompany.greenstock.controllers;

import com.mycompany.greenstock.services.SaleService;
import com.mycompany.greenstock.database.models.Sale;
import java.util.List;

public class SaleController {
    private final SaleService saleService = new SaleService();

    public String createSale(Sale sale) {
        try {
            saleService.createSale(sale);
            return "Sale created successfully";
        } catch (Exception e) {
            return "Error creating sale: " + e.getMessage();
        }
    }

    public Sale getSale(int id) {
        try {
            return saleService.getSaleById(id);
        } catch (Exception e) {
            System.err.println("Error getting sale: " + e.getMessage());
            return null;
        }
    }

    public List<Sale> getAllSales() {
        try {
            return saleService.getAllSales();
        } catch (Exception e) {
            System.err.println("Error getting sales: " + e.getMessage());
            return List.of();
        }
    }

    public String updateSale(Sale sale) {
        try {
            saleService.updateSale(sale);
            return "Sale updated successfully";
        } catch (Exception e) {
            return "Error updating sale: " + e.getMessage();
        }
    }

    public String deleteSale(int id) {
        try {
            saleService.deleteSale(id);
            return "Sale deleted successfully";
        } catch (Exception e) {
            return "Error deleting sale: " + e.getMessage();
        }
    }
} 