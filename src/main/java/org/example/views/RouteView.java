package org.example.views;

import org.example.models.entities.Route;
import org.example.models.repositories.RouteRepository;

import javax.swing.*;
import java.math.BigDecimal;

public class RouteView extends BaseView<Route> {
    private JTextField txtId, txtName, txtDistance, txtBasePayment, txtCompanyId;

    public RouteView(RouteRepository repository) {
        super("Маршруты",
                new String[]{"ID", "Название маршрута", "Примерное расстояние", "Базовая оплата", "ID Компании"},
                repository);
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    protected void initForm() {
        txtName = new JTextField();
        txtDistance = new JTextField();
        txtBasePayment = new JTextField();
        txtCompanyId = new JTextField();

        addFormField("ID:", txtId = new JTextField());
        addFormField("Название маршрута:", txtName);
        addFormField("Примерное расстояние:", txtDistance);
        addFormField("Базовая оплата:", txtBasePayment);
        addFormField("ID Компании:", txtCompanyId);
    }

    @Override
    protected void fillFormFromTable(int row) {
        txtId.setText(tableModel.getValueAt(row, 0).toString());
        txtName.setText(tableModel.getValueAt(row, 1).toString());
        txtDistance.setText(tableModel.getValueAt(row, 2).toString());
        txtBasePayment.setText(tableModel.getValueAt(row, 3).toString());
        txtCompanyId.setText(tableModel.getValueAt(row, 4).toString());
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        for (Route r : repository.getAll()) {
            tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getName(),
                    r.getEstimatedDistance(),
                    r.getBasePayment(),
                    r.getCompanyId()
            });
        }
        clearForm();
    }

    @Override
    protected void initActions() {
        btnCreate.addActionListener(e -> {
            try {
                Route route = parseForm();
                repository.create(route);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка ввода: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                Route route = parseForm();
                route.setId(Integer.parseInt(tableModel.getValueAt(table.getSelectedRow(), 0).toString()));
                repository.update(route);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка ввода: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tableModel.getValueAt(table.getSelectedRow(), 0).toString());
                repository.delete(id);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Неверный ID: " + ex.getMessage());
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

    private Route parseForm() throws Exception {
        int id = Integer.parseInt(txtId.getText().trim());
        String name = txtName.getText().trim();
        BigDecimal distance = new BigDecimal(txtDistance.getText().trim());
        BigDecimal basePayment = new BigDecimal(txtBasePayment.getText().trim());
        int companyId = Integer.parseInt(txtCompanyId.getText().trim());
        return new Route(id, name, distance, basePayment, companyId);
    }

    private void clearForm() {
        txtName.setText("");
        txtDistance.setText("");
        txtBasePayment.setText("");
        txtCompanyId.setText("");
    }
}