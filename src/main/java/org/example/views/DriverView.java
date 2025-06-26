package org.example.views;

import org.example.models.entities.Driver;
import org.example.models.repositories.DriverRepository;

import javax.swing.*;
import java.util.List;

public class DriverView extends BaseView<Driver> {
    private JTextField txtId, txtLastName, txtFirstName, txtMiddleName, txtExperienceYears;

    public DriverView(DriverRepository repository) {
        super("Водители", new String[]{"ID", "Фамилия", "Имя", "Отчество", "Стаж (лет)"}, repository);
        setSize(800, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    protected void initForm() {
        txtId = new JTextField();
        txtLastName = new JTextField();
        txtFirstName = new JTextField();
        txtMiddleName = new JTextField();
        txtExperienceYears = new JTextField();

        addFormField("ID:", txtId);
        addFormField("Фамилия:", txtLastName);
        addFormField("Имя:", txtFirstName);
        addFormField("Отчество:", txtMiddleName);
        addFormField("Стаж (лет):", txtExperienceYears);
    }

    @Override
    protected void fillFormFromTable(int row) {
        txtId.setText(tableModel.getValueAt(row, 0).toString());
        txtLastName.setText(tableModel.getValueAt(row, 1).toString());
        txtFirstName.setText(tableModel.getValueAt(row, 2).toString());
        txtMiddleName.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
        txtExperienceYears.setText(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "");
        txtId.setEditable(false);
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        List<Driver> drivers = repository.getAll();
        for (Driver d : drivers) {
            tableModel.addRow(new Object[]{
                    d.getId(),
                    d.getLastName(),
                    d.getFirstName(),
                    d.getMiddleName(),
                    d.getExperienceYears()
            });
        }
        clearForm();
    }

    @Override
    protected void initActions() {
        btnCreate.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String lastName = txtLastName.getText().trim();
                String firstName = txtFirstName.getText().trim();
                String middleName = txtMiddleName.getText().trim();
                Integer expYears = null;
                if (!txtExperienceYears.getText().trim().isEmpty()) {
                    expYears = Integer.parseInt(txtExperienceYears.getText().trim());
                }
                if (lastName.isEmpty() || firstName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Фамилия и имя не могут быть пустыми");
                    return;
                }
                repository.create(new Driver(id, lastName, firstName, middleName, expYears));
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Неверный числовой формат");
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String lastName = txtLastName.getText().trim();
                String firstName = txtFirstName.getText().trim();
                String middleName = txtMiddleName.getText().trim();
                Integer expYears = null;
                if (!txtExperienceYears.getText().trim().isEmpty()) {
                    expYears = Integer.parseInt(txtExperienceYears.getText().trim());
                }
                if (lastName.isEmpty() || firstName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Фамилия и имя не могут быть пустыми");
                    return;
                }
                repository.update(new Driver(id, lastName, firstName, middleName, expYears));
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Неверный числовой формат");
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                repository.delete(id);
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Неверный ID");
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

    private void clearForm() {
        txtId.setText("");
        txtLastName.setText("");
        txtFirstName.setText("");
        txtMiddleName.setText("");
        txtExperienceYears.setText("");
        txtId.setEditable(true);
    }
}