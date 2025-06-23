package org.example.views;

import org.example.models.entities.Driver;
import org.example.models.repositories.DriverRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DriverView extends JFrame {
    private DriverRepository repo = DriverRepository.getInstance();

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtId, txtLastName, txtFirstName, txtMiddleName, txtExperienceYears;
    private JButton btnCreate, btnUpdate, btnDelete, btnRefresh;

    public DriverView() {
        setTitle("Drivers");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Last Name", "First Name", "Middle Name", "Experience Years"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        formPanel.add(txtId);

        formPanel.add(new JLabel("Last Name:"));
        txtLastName = new JTextField();
        formPanel.add(txtLastName);

        formPanel.add(new JLabel("First Name:"));
        txtFirstName = new JTextField();
        formPanel.add(txtFirstName);

        formPanel.add(new JLabel("Middle Name:"));
        txtMiddleName = new JTextField();
        formPanel.add(txtMiddleName);

        formPanel.add(new JLabel("Experience Years:"));
        txtExperienceYears = new JTextField();
        formPanel.add(txtExperienceYears);

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

        btnCreate.addActionListener(e -> createDriver());
        btnUpdate.addActionListener(e -> updateDriver());
        btnDelete.addActionListener(e -> deleteDriver());
        btnRefresh.addActionListener(e -> loadData());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtId.setText(tableModel.getValueAt(row, 0).toString());
                    txtLastName.setText(tableModel.getValueAt(row, 1).toString());
                    txtFirstName.setText(tableModel.getValueAt(row, 2).toString());
                    txtMiddleName.setText(tableModel.getValueAt(row, 3).toString());
                    txtExperienceYears.setText(tableModel.getValueAt(row, 4) != null
                            ? tableModel.getValueAt(row, 4).toString() : "");
                    txtId.setEditable(false);
                }
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Driver> drivers = repo.getAll();
        for (Driver d : drivers) {
            tableModel.addRow(new Object[]{
                    d.getId(),
                    d.getLastName(),
                    d.getFirstName(),
                    d.getMiddleName(),
                    d.getExperienceYears()
            });
        }
        txtId.setEditable(true);
        txtId.setText("");
        txtLastName.setText("");
        txtFirstName.setText("");
        txtMiddleName.setText("");
        txtExperienceYears.setText("");
    }

    private void createDriver() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String lastName = txtLastName.getText();
            String firstName = txtFirstName.getText();
            String middleName = txtMiddleName.getText();
            Integer expYears = null;
            if (!txtExperienceYears.getText().isEmpty()) {
                expYears = Integer.parseInt(txtExperienceYears.getText());
            }
            if (lastName.isEmpty() || firstName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Last Name and First Name cannot be empty");
                return;
            }
            repo.create(new Driver(id, lastName, firstName, middleName, expYears));
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format");
        }
    }

    private void updateDriver() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String lastName = txtLastName.getText();
            String firstName = txtFirstName.getText();
            String middleName = txtMiddleName.getText();
            Integer expYears = null;
            if (!txtExperienceYears.getText().isEmpty()) {
                expYears = Integer.parseInt(txtExperienceYears.getText());
            }
            if (lastName.isEmpty() || firstName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Last Name and First Name cannot be empty");
                return;
            }
            repo.update(new Driver(id, lastName, firstName, middleName, expYears));
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format");
        }
    }

    private void deleteDriver() {
        try {
            int id = Integer.parseInt(txtId.getText());
            repo.delete(id);
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
        }
    }
}
