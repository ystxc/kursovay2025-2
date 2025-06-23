package org.example.views;

import org.example.models.entities.Company;
import org.example.models.repositories.CompanyRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CompanyView extends JFrame {
    private CompanyRepository repo = CompanyRepository.getInstance();

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtId, txtName;
    private JButton btnCreate, btnUpdate, btnDelete, btnRefresh;

    public CompanyView() {
        setTitle("Companies");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Таблица
        tableModel = new DefaultTableModel(new String[]{"ID", "Name"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Форма для ввода
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        formPanel.add(txtId);
        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        // Кнопки
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnCreate = new JButton("Create");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        loadData();

        btnCreate.addActionListener(e -> createCompany());
        btnUpdate.addActionListener(e -> updateCompany());
        btnDelete.addActionListener(e -> deleteCompany());
        btnRefresh.addActionListener(e -> loadData());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtId.setText(tableModel.getValueAt(row, 0).toString());
                    txtName.setText(tableModel.getValueAt(row, 1).toString());
                    txtId.setEditable(false); // ID не меняется при обновлении
                }
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Company> companies = repo.getAll();
        for (Company c : companies) {
            tableModel.addRow(new Object[]{c.getId(), c.getName()});
        }
        txtId.setEditable(true);
        txtId.setText("");
        txtName.setText("");
    }

    private void createCompany() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty");
                return;
            }
            repo.create(new Company(id, name));
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
        }
    }

    private void updateCompany() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty");
                return;
            }
            repo.update(new Company(id, name));
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
        }
    }

    private void deleteCompany() {
        try {
            int id = Integer.parseInt(txtId.getText());
            repo.delete(id);
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
        }
    }
}
