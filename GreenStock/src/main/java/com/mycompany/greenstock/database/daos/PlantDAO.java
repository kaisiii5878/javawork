package com.mycompany.greenstock.database.daos;

import com.mycompany.greenstock.database.BaseDAO;
import com.mycompany.greenstock.database.models.Plant;
import com.mycompany.greenstock.database.DatabaseConnection;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlantDAO extends BaseDAO<Plant> {

    @Override
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    protected String getTableName() {
        return "plants";
    }

    @Override
    protected Plant mapResultSetToEntity(ResultSet rs) throws SQLException {
        Plant plant = new Plant();
        plant.setId(rs.getInt("id"));
        plant.setScientificName(rs.getString("scientific_name"));
        plant.setCommonName(rs.getString("common_name"));
        plant.setDescription(rs.getString("description"));
        plant.setPlantType(rs.getString("plant_type"));
        plant.setGrowthRate(rs.getString("growth_rate"));
        plant.setMatureSize(rs.getString("mature_size"));
        plant.setSunRequirements(rs.getString("sun_requirements"));
        plant.setWaterRequirements(rs.getString("water_requirements"));
        plant.setSoilType(rs.getString("soil_type"));
        plant.setHardinessZone(rs.getString("hardiness_zone"));
        return plant;
    }

    @Override
    protected void setCreateParameters(PreparedStatement stmt, Plant plant) throws SQLException {
        stmt.setString(1, plant.getScientificName());
        stmt.setString(2, plant.getCommonName());
        stmt.setString(3, plant.getDescription());
        stmt.setString(4, plant.getPlantType());
        stmt.setString(5, plant.getGrowthRate());
        stmt.setString(6, plant.getMatureSize());
        stmt.setString(7, plant.getSunRequirements());
        stmt.setString(8, plant.getWaterRequirements());
        stmt.setString(9, plant.getSoilType());
        stmt.setString(10, plant.getHardinessZone());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Plant plant) throws SQLException {
        stmt.setString(1, plant.getScientificName());
        stmt.setString(2, plant.getCommonName());
        stmt.setString(3, plant.getDescription());
        stmt.setString(4, plant.getPlantType());
        stmt.setString(5, plant.getGrowthRate());
        stmt.setString(6, plant.getMatureSize());
        stmt.setString(7, plant.getSunRequirements());
        stmt.setString(8, plant.getWaterRequirements());
        stmt.setString(9, plant.getSoilType());
        stmt.setString(10, plant.getHardinessZone());
        stmt.setInt(11, plant.getId());
    }

    @Override
    protected int getParameterCount(Plant plant) {
        return 10; // scientific_name, common_name, description, plant_type, growth_rate, mature_size, sun_requirements, water_requirements, soil_type, hardiness_zone
    }

    @Override
    protected String getColumnNames(Plant plant) {
        return "scientific_name, common_name, description, plant_type, growth_rate, mature_size, sun_requirements, water_requirements, soil_type, hardiness_zone";
    }

    // CRUD Operations
    public void create(Plant plant) throws SQLException {
        String query = "INSERT INTO " + getTableName() + 
            " (scientific_name, common_name, description, plant_type, growth_rate, mature_size, sun_requirements, water_requirements, soil_type, hardiness_zone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setCreateParameters(stmt, plant);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error in DAO: " + e.getMessage());
        }
    }

    public Plant read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        }
        return null;
    }

    public void update(Plant plant) throws SQLException {
        String query = "UPDATE " + getTableName() + 
            " SET scientific_name = ?, common_name = ?, description = ?, plant_type = ?, growth_rate = ?, mature_size = ?, sun_requirements = ?, water_requirements = ?, soil_type = ?, hardiness_zone = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setUpdateParameters(stmt, plant);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Plant> getAll() throws SQLException {
        List<Plant> plants = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                plants.add(mapResultSetToEntity(rs));
            }
        }
        return plants;
    }
} 