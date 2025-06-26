package org.example.views;

import org.example.models.entities.Company;
import org.example.models.repositories.CompanyRepository;

import javax.swing.*;
import java.util.List;

public class CompanyView extends BaseView<Company> {
    private JTextField txtId;
    private JTextField txtName;

    public CompanyView(CompanyRepository repository) {
        super("Компании", new String[]{"ID", "Название компании"}, repository);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    protected void initForm() {
        addFormField("ID:", txtId = new JTextField());
        addFormField("Название компании:", txtName = new JTextField());
    }

    @Override
    protected void fillFormFromTable(int row) {
        txtId.setText(tableModel.getValueAt(row, 0).toString());
        txtName.setText(tableModel.getValueAt(row, 1).toString());
        txtId.setEditable(false);
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        List<Company> companies = repository.getAll();
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
                    JOptionPane.showMessageDialog(this, "Название компании не может быть пустым");
                    return;
                }
                repository.create(new Company(id, name));
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Неверный ID");
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String name = txtName.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Название компании не может быть пустым");
                    return;
                }
                repository.update(new Company(id, name));
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Неверный ID");
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
        txtName.setText("");
        txtId.setEditable(true);
    }
}