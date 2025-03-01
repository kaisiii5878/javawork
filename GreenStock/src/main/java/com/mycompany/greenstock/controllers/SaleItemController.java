package com.mycompany.greenstock.controllers;

import com.mycompany.greenstock.services.SaleItemService;
import com.mycompany.greenstock.database.models.SaleItem;
import java.util.List;

public class SaleItemController {
    private final SaleItemService saleItemService = new SaleItemService();

    public String createSaleItem(SaleItem saleItem) {
        try {
            saleItemService.createSaleItem(saleItem);
            return "Sale item created successfully";
        } catch (Exception e) {
            return "Error creating sale item: " + e.getMessage();
        }
    }

    public List<SaleItem> getItemsBySale(int saleId) {
        try {
            return saleItemService.getItemsBySaleId(saleId);
        } catch (Exception e) {
            System.err.println("Error getting sale items: " + e.getMessage());
            return List.of();
        }
    }

    public String updateSaleItem(SaleItem saleItem) {
        try {
            saleItemService.updateSaleItem(saleItem);
            return "Sale item updated successfully";
        } catch (Exception e) {
            return "Error updating sale item: " + e.getMessage();
        }
    }

    public String deleteSaleItem(int id) {
        try {
            saleItemService.deleteSaleItem(id);
            return "Sale item deleted successfully";
        } catch (Exception e) {
            return "Error deleting sale item: " + e.getMessage();
        }
    }

    public SaleItem getSaleItem(int id) {
        try {
            return saleItemService.getSaleItemById(id);
        } catch (Exception e) {
            System.err.println("Error getting sale item: " + e.getMessage());
            return null;
        }
    }
} 