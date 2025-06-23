package org.example.models.repositories;

import org.example.models.entities.RouteDriver;
import java.util.ArrayList;
import java.util.List;

public class RouteDriverRepository implements Repository<RouteDriver> {

    private static RouteDriverRepository instance;

    // Пример внутреннего хранилища, замени на свою реализацию
    private final List<RouteDriver> storage = new ArrayList<>();

    private RouteDriverRepository() {
        // private конструктор с инициализацией, если нужно
    }

    public static RouteDriverRepository getInstance() {
        if (instance == null) {
            instance = new RouteDriverRepository();
        }
        return instance;
    }

    @Override
    public List<RouteDriver> getAll() {
        // Возвращаем копию списка, чтобы внешний код не менял внутреннее хранилище напрямую
        return new ArrayList<>(storage);
    }

    @Override
    public void create(RouteDriver entity) {
        storage.add(entity);
    }

    @Override
    public void update(RouteDriver entity) {
        // Найдем существующий элемент по составному ключу и обновим его
        for (int i = 0; i < storage.size(); i++) {
            RouteDriver rd = storage.get(i);
            if (rd.getRouteId() == entity.getRouteId() && rd.getDriverId() == entity.getDriverId()) {
                storage.set(i, entity);
                return;
            }
        }
        throw new IllegalArgumentException("Entity not found for update");
    }

    @Override
    public void delete(int id) {
        // Поскольку у RouteDriver составной ключ, метод delete по одному id не применим
        throw new UnsupportedOperationException("Use delete(routeId, driverId) instead.");
    }

    // Метод удаления по составному ключу (routeId и driverId)
    public void delete(int routeId, int driverId) {
        storage.removeIf(rd -> rd.getRouteId() == routeId && rd.getDriverId() == driverId);
    }
}
