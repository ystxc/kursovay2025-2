package org.example.views;

import org.example.models.entities.RouteDriver;
import org.example.models.repositories.RouteDriverRepository;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class RouteDriverView extends BaseView<RouteDriver> {
    private JTextField txtRouteId, txtDriverId, txtDepartureDate, txtArrivalDate, txtBonusStatus, txtActualPayment;

    public RouteDriverView(RouteDriverRepository repository) {
        super("Маршруты-Водители", new String[]{"ID Маршрута", "ID Водителя", "Дата отправления", "Дата прибытия", "Статус премии", "Выплата"}, repository);
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

        addFormField("ID Маршрута:", txtRouteId);
        addFormField("ID Водителя:", txtDriverId);
        addFormField("Дата отправления (ГГГГ-ММ-ДД):", txtDepartureDate);
        addFormField("Дата прибытия (ГГГГ-ММ-ДД):", txtArrivalDate);
        addFormField("Статус премии (да/нет):", txtBonusStatus);
        addFormField("Сумма выплаты:", txtActualPayment);
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
        List<RouteDriver> list = repository.getAll();
        for (RouteDriver rd : list) {
            tableModel.addRow(new Object[]{
                    rd.getRouteId(),
                    rd.getDriverId(),
                    rd.getArrivalDate(),
                    rd.getDepartureDate(),
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
                JOptionPane.showMessageDialog(this, "Ошибка ввода: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                RouteDriver rd = parseForm();
                repository.update(rd);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка ввода: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                int routeId = Integer.parseInt(txtRouteId.getText().trim());
                int driverId = Integer.parseInt(txtDriverId.getText().trim());
                ((RouteDriverRepository) repository).delete(routeId, driverId);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Неверная пара ID: " + ex.getMessage());
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