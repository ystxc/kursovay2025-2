package org.example.models.repositories;

import org.example.models.entities.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository implements Repository<Company> {
    private static CompanyRepository instance;
    private Connection connection;
    private List<Company> cache = new ArrayList<>();

    private CompanyRepository() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/cargo_transportation?useSSL=false&serverTimezone=UTC",
                    "root",
                    ""
            );
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static CompanyRepository getInstance() {
        if (instance == null) {
            synchronized (CompanyRepository.class) {
                if (instance == null) {
                    instance = new CompanyRepository();
                }
            }
        }
        return instance;
    }

    @Override
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
        return new ArrayList<>(cache);
    }

    @Override
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

    @Override
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

    @Override
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
