package org.example.models.repositories;

import org.example.models.entities.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository implements Repository<Driver> {
    private static volatile DriverRepository instance;
    private Connection connection;

    private DriverRepository() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/cargo_transportation?useSSL=false&serverTimezone=UTC",
                    "root",
                    ""
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize DriverRepository", e);
        }
    }

    public static DriverRepository getInstance() {
        if (instance == null) {
            synchronized (DriverRepository.class) {
                if (instance == null) {
                    instance = new DriverRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Driver> getAll() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM drivers";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                drivers.add(new Driver(
                        rs.getInt("id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("middle_name"),
                        rs.getInt("experience_years")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    @Override
    public void create(Driver driver) {
        String sql = "INSERT INTO drivers (id, last_name, first_name, middle_name, experience_years) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, driver.getId());
            stmt.setString(2, driver.getLastName());
            stmt.setString(3, driver.getFirstName());
            stmt.setString(4, driver.getMiddleName());
            if (driver.getExperienceYears() != null) {
                stmt.setInt(5, driver.getExperienceYears());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Driver driver) {
        String sql = "UPDATE drivers SET last_name = ?, first_name = ?, middle_name = ?, experience_years = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, driver.getLastName());
            stmt.setString(2, driver.getFirstName());
            stmt.setString(3, driver.getMiddleName());
            if (driver.getExperienceYears() != null) {
                stmt.setInt(4, driver.getExperienceYears());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setInt(5, driver.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM drivers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
