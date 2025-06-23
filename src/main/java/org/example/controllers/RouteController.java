package org.example.controllers;

import org.example.models.entities.Route;
import org.example.models.repositories.RouteRepository;

import java.util.List;

public class RouteController {
    private RouteRepository repository;

    public RouteController() {
        this.repository = RouteRepository.getInstance();
    }

    public List<Route> getAllRoutes() {
        return repository.getAll();
    }

    public void addRoute(Route route) {
        repository.create(route);
    }

    public void updateRoute(Route route) {
        repository.update(route);
    }

    public void deleteRoute(int routeId) {
        repository.delete(routeId);
    }
}
