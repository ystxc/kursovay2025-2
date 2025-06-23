package org.example.views;

import org.example.models.entities.RouteDriver;
import org.example.models.repositories.RouteDriverRepository;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.Date;

public class RouteDriverView extends BaseView<RouteDriver> {
    private JTextField txtRouteId, txtDriverId, txtDepartureDate, txtArrivalDate, txtBonusStatus, txtActualPayment;

    public RouteDriverView() {
        super("Route Drivers",
                new String[]{"Route ID", "Driver ID", "Departure Date", "Arrival Date", "Bonus Status", "Actual Payment"},
                RouteDriverRepository.getInstance());
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    protected void initForm() {
        txtRouteId = new JTextField();
        txtDriverId = new JTextField();
        txtDepartureDate = new JTextField();
        txtArrivalDate = new JTextField();
        txtBonusStatus = new JTextField();
        txtActualPayment = new JTextField();

        addFormField("Route ID:", txtRouteId);
        addFormField("Driver ID:", txtDriverId);
        addFormField("Departure Date (YYYY-MM-DD):", txtDepartureDate);
        addFormField("Arrival Date (YYYY-MM-DD):", txtArrivalDate);
        addFormField("Bonus Status (true/false):", txtBonusStatus);
        addFormField("Actual Payment:", txtActualPayment);
    }

    @Override
    protected void fillFormFromTable(int row) {
        txtRouteId.setText(tableModel.getValueAt(row, 0).toString());
        txtDriverId.setText(tableModel.getValueAt(row, 1).toString());
        txtDepartureDate.setText(tableModel.getValueAt(row, 2) != null ? tableModel.getValueAt(row, 2).toString() : "");
        txtArrivalDate.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
        txtBonusStatus.setText(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "");
        txtActualPayment.setText(tableModel.getValueAt(row, 5) != null ? tableModel.getValueAt(row, 5).toString() : "");
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
        clearForm();
    }

    @Override
    protected void initActions() {
        btnCreate.addActionListener(e -> {
            try {
                RouteDriver rd = parseForm();
                repository.create(rd);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                RouteDriver rd = parseForm();
                repository.update(rd);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                int routeId = Integer.parseInt(txtRouteId.getText().trim());
                int driverId = Integer.parseInt(txtDriverId.getText().trim());
                // Удаляем запись по составному ключу
                ((RouteDriverRepository) repository).delete(routeId, driverId);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID pair: " + ex.getMessage());
            }
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    fillFormFromTable(row);
                }
            }
        });
    }

    private RouteDriver parseForm() throws Exception {
        int routeId = Integer.parseInt(txtRouteId.getText().trim());
        int driverId = Integer.parseInt(txtDriverId.getText().trim());
        // Преобразуем даты из строки в LocalDate через java.sql.Date
        var departureDate = Date.valueOf(txtDepartureDate.getText().trim()).toLocalDate();
        var arrivalDate = Date.valueOf(txtArrivalDate.getText().trim()).toLocalDate();
        boolean bonusStatus = Boolean.parseBoolean(txtBonusStatus.getText().trim());
        BigDecimal actualPayment = new BigDecimal(txtActualPayment.getText().trim());

        return new RouteDriver(routeId, driverId, departureDate, arrivalDate, bonusStatus, actualPayment);
    }

    private void clearForm() {
        txtRouteId.setText("");
        txtDriverId.setText("");
        txtDepartureDate.setText("");
        txtArrivalDate.setText("");
        txtBonusStatus.setText("");
        txtActualPayment.setText("");
    }
}
