package org.example.controllers;

import models.entities.RouteDriver;
import models.repositories.RouteDriverRepository;

import java.util.List;

public class RouteDriverController {
    private RouteDriverRepository repository;

    public RouteDriverController() {
        this.repository = RouteDriverRepository.getInstance();
    }

    public List<RouteDriver> getAllRouteDrivers() {
        return repository.getAll();
    }

    public void addRouteDriver(RouteDriver routeDriver) {
        repository.create(routeDriver);
    }

    public void updateRouteDriver(RouteDriver routeDriver) {
        repository.update(routeDriver);
    }

    public void deleteRouteDriver(int routeId, int driverId) {
        repository.delete(routeId, driverId);
    }
}
