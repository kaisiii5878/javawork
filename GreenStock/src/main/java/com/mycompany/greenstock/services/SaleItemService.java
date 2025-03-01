package com.mycompany.greenstock.services;

import com.mycompany.greenstock.database.daos.SaleItemDAO;
import com.mycompany.greenstock.database.models.SaleItem;
import java.sql.SQLException;
import java.util.List;

public class SaleItemService {
    private final SaleItemDAO saleItemDAO;

    public SaleItemService() {
        this.saleItemDAO = new SaleItemDAO();
    }

    public void createSaleItem(SaleItem saleItem) throws SQLException {
        saleItemDAO.create(saleItem);
    }

    public SaleItem getSaleItemById(int id) throws SQLException {
        return saleItemDAO.read(id);
    }

    public List<SaleItem> getAllSaleItems() throws SQLException {
        return saleItemDAO.getAll();
    }

    public void updateSaleItem(SaleItem saleItem) throws SQLException {
        saleItemDAO.update(saleItem);
    }

    public void deleteSaleItem(int id) throws SQLException {
        saleItemDAO.delete(id);
    }

    public List<SaleItem> getItemsBySaleId(int saleId) throws SQLException {
        return saleItemDAO.getItemsBySaleId(saleId);
    }
} 