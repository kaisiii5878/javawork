package com.mycompany.greenstock.services;

import com.mycompany.greenstock.database.daos.SaleDAO;
import com.mycompany.greenstock.database.models.Sale;
import java.sql.SQLException;
import java.util.List;

public class SaleService {
    private final SaleDAO saleDAO;

    public SaleService() {
        this.saleDAO = new SaleDAO();
    }

    public void createSale(Sale sale) throws SQLException {
        saleDAO.create(sale);
    }

    public Sale getSaleById(int id) throws SQLException {
        return saleDAO.read(id);
    }

    public List<Sale> getAllSales() throws SQLException {
        return saleDAO.getAll();
    }

    public void updateSale(Sale sale) throws SQLException {
        saleDAO.update(sale);
    }

    public void deleteSale(int id) throws SQLException {
        saleDAO.delete(id);
    }
} 