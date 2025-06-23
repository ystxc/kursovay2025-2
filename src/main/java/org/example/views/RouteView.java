package org.example.views;

import org.example.models.entities.Route;
import org.example.models.repositories.RouteRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;

public class RouteView extends JFrame {
    private RouteRepository repo = RouteRepository.getInstance();

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtId, txtName, txtEstimatedDistance, txtBasePayment, txtCompanyId;
    private JButton btnCreate, btnUpdate, btnDelete, btnRefresh;

    public RouteView() {
        setTitle("Routes");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Estimated Distance", "Base Payment", "Company ID"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        formPanel.add(txtId);

        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Estimated Distance:"));
        txtEstimatedDistance = new JTextField();
        formPanel.add(txtEstimatedDistance);

        formPanel.add(new JLabel("Base Payment:"));
        txtBasePayment = new JTextField();
        formPanel.add(txtBasePayment);

        formPanel.add(new JLabel("Company ID:"));
        txtCompanyId = new JTextField();
        formPanel.add(txtCompanyId);

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

        btnCreate.addActionListener(e -> createRoute());
        btnUpdate.addActionListener(e -> updateRoute());
        btnDelete.addActionListener(e -> deleteRoute());
        btnRefresh.addActionListener(e -> loadData());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtId.setText(tableModel.getValueAt(row, 0).toString());
                    txtName.setText(tableModel.getValueAt(row, 1).toString());
                    txtEstimatedDistance.setText(tableModel.getValueAt(row, 2).toString());
                    txtBasePayment.setText(tableModel.getValueAt(row, 3).toString());
                    txtCompanyId.setText(tableModel.getValueAt(row, 4).toString());
                    txtId.setEditable(false);
                }
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Route> routes = repo.getAll();
        for (Route r : routes) {
            tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getName(),
                    r.getEstimatedDistance(),
                    r.getBasePayment(),
                    r.getCompanyId()
            });
        }
        txtId.setEditable(true);
        txtId.setText("");
        txtName.setText("");
        txtEstimatedDistance.setText("");
        txtBasePayment.setText("");
        txtCompanyId.setText("");
    }

    private void createRoute() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            BigDecimal estimatedDistance = new BigDecimal(txtEstimatedDistance.getText());
            BigDecimal basePayment = new BigDecimal(txtBasePayment.getText());
            int companyId = Integer.parseInt(txtCompanyId.getText());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty");
                return;
            }

            repo.create(new Route(id, name, estimatedDistance, basePayment, companyId));
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format");
        }
    }

    private void updateRoute() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            BigDecimal estimatedDistance = new BigDecimal(txtEstimatedDistance.getText());
            BigDecimal basePayment = new BigDecimal(txtBasePayment.getText());
            int companyId = Integer.parseInt(txtCompanyId.getText());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty");
                return;
            }

            repo.update(new Route(id, name, estimatedDistance, basePayment, companyId));
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format");
        }
    }

    private void deleteRoute() {
        try {
            int id = Integer.parseInt(txtId.getText());
            repo.delete(id);
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
        }
    }
}
