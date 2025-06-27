package org.example.models.repositories;

import org.example.models.entities.RouteDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RouteDriverRepository implements Repository<RouteDriver> {

    private static RouteDriverRepository instance;
    private java.sql.Connection connection;

    private final List<RouteDriver> storage = new ArrayList<>();

    private RouteDriverRepository() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/cargo_transportation?useSSL=false&serverTimezone=UTC",
                    "root",
                    ""
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RouteDriverRepository getInstance() {
        if (instance == null) {
            instance = new RouteDriverRepository();
        }
        return instance;
    }

    @Override
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




    @Override
    public void create(RouteDriver entity) {
        Objects.requireNonNull(entity, "RouteDriver cannot be null");
        final String sql = """
            INSERT INTO route_drivers 
            (route_id, driver_id, departure_date, arrival_date, bonus_status, actual_payment) 
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setParameters(stmt, entity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create route-driver assignment: " + entity, e);
        }
    }

    @Override
    public void update(RouteDriver entity) {
        Objects.requireNonNull(entity, "RouteDriver cannot be null");
        final String sql = """
            UPDATE route_drivers 
            SET departure_date = ?, arrival_date = ?, bonus_status = ?, actual_payment = ? 
            WHERE route_id = ? AND driver_id = ?
            """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(entity.getDepartureDate().toLocalDate()));
            stmt.setDate(2, Date.valueOf(entity.getArrivalDate().toLocalDate()));
            stmt.setBoolean(3, entity.isBonusStatus());
            stmt.setBigDecimal(4, entity.getActualPayment());
            stmt.setInt(5, entity.getRouteId());
            stmt.setInt(6, entity.getDriverId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Route-driver assignment not found for update");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update route-driver assignment: " + entity, e);
        }
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Use delete(int routeId, int driverId) instead");
    }

    public void delete(int routeId, int driverId) {
        final String sql = "DELETE FROM route_drivers WHERE route_id = ? AND driver_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, routeId);
            stmt.setInt(2, driverId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Route-driver assignment not found for deletion");
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    String.format("Failed to delete route-driver assignment [routeId=%d, driverId=%d]",
                            routeId, driverId), e);
        }
    }
    private void setParameters(PreparedStatement stmt, RouteDriver entity) throws SQLException {
        stmt.setInt(1, entity.getRouteId());
        stmt.setInt(2, entity.getDriverId());
        stmt.setDate(3, Date.valueOf(entity.getDepartureDate().toLocalDate()));
        stmt.setDate(4, Date.valueOf(entity.getArrivalDate().toLocalDate()));
        stmt.setBoolean(5, entity.isBonusStatus());
        stmt.setBigDecimal(6, entity.getActualPayment());
    }
}
