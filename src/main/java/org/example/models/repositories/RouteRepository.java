package org.example.models.repositories;

import org.example.models.entities.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteRepository {
    private static RouteRepository instance;
    private Connection connection;

    private RouteRepository() {
        try {
            // 1. Регистрация драйвера (для Java 9+)
            Class.forName("org.mariadb.jdbc.Driver");

            // 2. Установка соединения (ДОБАВЬТЕ ВАШИ РЕАЛЬНЫЕ ЛОГИН/ПАРОЛЬ!)
            connection = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/cargo_transportation?useSSL=false&serverTimezone=UTC",
                    "root",
                    ""
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RouteRepository getInstance() {
        if (instance == null) instance = new RouteRepository();
        return instance;
    }

    public List<Route> getAll() {
        List<Route> routes = new ArrayList<>();
        String sql = "SELECT * FROM routes";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                routes.add(new Route(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("estimated_distance"),
                        rs.getBigDecimal("base_payment"),
                        rs.getInt("company_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    public void create(Route route) {
        String sql = "INSERT INTO routes (id, name, estimated_distance, base_payment, company_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, route.getId());
            stmt.setString(2, route.getName());
            stmt.setBigDecimal(3, route.getEstimatedDistance());
            stmt.setBigDecimal(4, route.getBasePayment());
            stmt.setInt(5, route.getCompanyId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Route route) {
        String sql = "UPDATE routes SET name = ?, estimated_distance = ?, base_payment = ?, company_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, route.getName());
            stmt.setBigDecimal(2, route.getEstimatedDistance());
            stmt.setBigDecimal(3, route.getBasePayment());
            stmt.setInt(4, route.getCompanyId());
            stmt.setInt(5, route.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM routes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
