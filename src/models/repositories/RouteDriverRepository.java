package models.repositories;

import models.entities.RouteDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDriverRepository {
    private static RouteDriverRepository instance;
    private Connection connection;

    private RouteDriverRepository() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cargo_transportation", " ", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RouteDriverRepository getInstance() {
        if (instance == null) instance = new RouteDriverRepository();
        return instance;
    }

    public List<RouteDriver> getAll() {
        List<RouteDriver> routeDrivers = new ArrayList<>();
        String sql = "SELECT * FROM route_drivers";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                routeDrivers.add(new RouteDriver(
                        rs.getInt("route_id"),
                        rs.getInt("driver_id"),
                        rs.getDate("departure_date").toLocalDate(),
                        rs.getDate("arrival_date").toLocalDate(),
                        rs.getBoolean("bonus_status"),
                        rs.getBigDecimal("actual_payment")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routeDrivers;
    }

    // Создание записи
    public void create(RouteDriver routeDriver) {
        String sql = "INSERT INTO route_drivers " +
                "(route_id, driver_id, departure_date, arrival_date, bonus_status, actual_payment) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, routeDriver.getRouteId());
            stmt.setInt(2, routeDriver.getDriverId());
            stmt.setDate(3, routeDriver.getDepartureDate());
            stmt.setDate(4, routeDriver.getArrivalDate());
            stmt.setBoolean(5, routeDriver.isBonusStatus());
            stmt.setBigDecimal(6, routeDriver.getActualPayment());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Обновление записи
    public void update(RouteDriver routeDriver) {
        String sql = "UPDATE route_drivers SET departure_date = ?, arrival_date = ?, " +
                "bonus_status = ?, actual_payment = ? " +
                "WHERE route_id = ? AND driver_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, routeDriver.getDepartureDate());
            stmt.setDate(2, routeDriver.getArrivalDate());
            stmt.setBoolean(3, routeDriver.isBonusStatus());
            stmt.setBigDecimal(4, routeDriver.getActualPayment());
            stmt.setInt(5, routeDriver.getRouteId());
            stmt.setInt(6, routeDriver.getDriverId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление записи по составному ключу
    public void delete(int routeId, int driverId) {
        String sql = "DELETE FROM route_drivers WHERE route_id = ? AND driver_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, routeId);
            stmt.setInt(2, driverId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

