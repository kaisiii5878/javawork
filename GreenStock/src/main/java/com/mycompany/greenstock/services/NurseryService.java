package com.mycompany.greenstock.services;

import com.mycompany.greenstock.database.daos.NurseryDAO;
import com.mycompany.greenstock.database.models.Nursery;
import java.sql.SQLException;
import java.util.List;

public class NurseryService {
    private final NurseryDAO nurseryDAO;

    public NurseryService() {
        this.nurseryDAO = new NurseryDAO();
    }

    public void createNursery(Nursery nursery) throws SQLException {
        nurseryDAO.create(nursery);
    }

    public Nursery getNurseryById(int id) throws SQLException {
        return nurseryDAO.read(id);
    }

    public List<Nursery> getAllNurseries() throws SQLException {
        return nurseryDAO.getAll();
    }

    public void updateNursery(Nursery nursery) throws SQLException {
        nurseryDAO.update(nursery);
    }

    public void deleteNursery(int id) throws SQLException {
        nurseryDAO.delete(id);
    }
} 