package org.example.views;

import org.example.models.entities.RouteDriver;
import org.example.models.repositories.RouteDriverRepository;

import javax.swing.*;
import java.awt.*;

public class RouteDriverViewReadOnly extends BaseView<RouteDriver> {

    public RouteDriverViewReadOnly() {
        super("Route-Drivers (Read-Only)",
                new String[]{"Route ID", "Driver ID", "Departure Date", "Arrival Date", "Bonus Status", "Actual Payment"},
                RouteDriverRepository.getInstance());

        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Отключаем кнопки, так как это Read-Only
        btnCreate.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnRefresh.addActionListener(e -> loadData());  // Оставим кнопку Refresh

        // Убираем форму, если она не нужна
        formPanel.setVisible(false);
    }

    @Override
    protected void initForm() {
        // Не нужно реализовывать, т.к. форма скрыта
    }

    @Override
    protected void fillFormFromTable(int row) {
        // Можно не реализовывать, т.к. нет редактирования
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        for (RouteDriver rd : repository.getAll()) {
            tableModel.addRow(new Object[]{
                    rd.getRouteId(),
                    rd.getDriverId(),
                    rd.getDepartureDate(),
                    rd.getArrivalDate(),
                    rd.isBonusStatus(),
                    rd.getActualPayment()
            });
        }
    }

    @Override
    protected void initActions() {
        // Отключаем кнопки, добавляем только refresh
        btnRefresh.addActionListener(e -> loadData());
    }
}
