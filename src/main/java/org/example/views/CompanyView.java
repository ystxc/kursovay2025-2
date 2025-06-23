package org.example.views;

import org.example.models.entities.Company;
import org.example.models.repositories.CompanyRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CompanyView extends BaseView<Company> {
    private JTextField txtId;
    private JTextField txtName;

    // Конструктор: передаём репозиторий в super
    public CompanyView() {
        super("Companies", new String[]{"ID", "Name"}, CompanyRepository.getInstance());
    }

    @Override
    protected void initForm() {
        // Форма с 2 полями и лейблами
        addFormField("ID:", txtId = new JTextField());
        addFormField("Name:", txtName = new JTextField());
    }

    @Override
    protected void fillFormFromTable(int row) {
        txtId.setText(tableModel.getValueAt(row, 0).toString());
        txtName.setText(tableModel.getValueAt(row, 1).toString());
        txtId.setEditable(false);  // ID редактировать нельзя при обновлении
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        List<Company> companies = repository.getAll();  // используем поле из BaseView
        for (Company c : companies) {
            tableModel.addRow(new Object[]{c.getId(), c.getName()});
        }
        clearForm();
    }

    @Override
    protected void initActions() {
        btnCreate.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String name = txtName.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name cannot be empty");
                    return;
                }
                repository.create(new Company(id, name));
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID");
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String name = txtName.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name cannot be empty");
                    return;
                }
                repository.update(new Company(id, name));
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID");
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                repository.delete(id);
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID");
            }
        });
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtId.setEditable(true);
    }
}
