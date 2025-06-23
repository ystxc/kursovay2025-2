package models.repositories;

import models.entities.Company;

import java.sql.*;
import java.util.*;

public class CompanyRepository {
    private static CompanyRepository instance;
    private Connection connection;
    private List<Company> cache = new ArrayList<>();

    private CompanyRepository() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cargo_transportation", " ", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static CompanyRepository getInstance() {
        if (instance == null) instance = new CompanyRepository();
        return instance;
    }

    public List<Company> getAll() {
        cache.clear();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM companies")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cache.add(new Company(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cache;
    }

    public void create(Company company) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO companies (id, name) VALUES (?, ?)")) {
            stmt.setInt(1, company.getId());
            stmt.setString(2, company.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Company company) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE companies SET name = ? WHERE id = ?")) {
            stmt.setString(1, company.getName());
            stmt.setInt(2, company.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM companies WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
