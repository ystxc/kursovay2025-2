package org.example.models.repositories;

import org.example.models.entities.RouteDriver;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        storage.add(entity);
    }

    @Override
    public void update(RouteDriver entity) {

        for (int i = 0; i < storage.size(); i++) {
            RouteDriver rd = storage.get(i);
            if (rd.getRouteId() == entity.getRouteId() && rd.getDriverId() == entity.getDriverId()) {
                storage.set(i, entity);
                return;
            }
        }
        throw new IllegalArgumentException("Объект для обновления не найден");
    }

    @Override
    public void delete(int id) {
        // составной ключ
        throw new UnsupportedOperationException("Вместо этого используйте delete(идентификатор маршрута, идентификатор водителя).");
    }
    // Метод удаления по составному ключу
    public void delete(int routeId, int driverId) {
        storage.removeIf(rd -> rd.getRouteId() == routeId && rd.getDriverId() == driverId);
    }
}
