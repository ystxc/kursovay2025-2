package org.example.controllers;

import org.example.models.entities.Driver;
import org.example.models.repositories.DriverRepository;

import java.util.List;

public class DriverController {
    private DriverRepository repository;

    public DriverController() {
        this.repository = DriverRepository.getInstance();
    }

    public List<Driver> getAllDrivers() {
        return repository.getAll();
    }

    public void addDriver(Driver driver) {
        // Здесь можно добавить валидацию
        repository.create(driver);
    }

    public void updateDriver(Driver driver) {
        repository.update(driver);
    }

    public void deleteDriver(int driverId) {
        repository.delete(driverId);
    }
}
