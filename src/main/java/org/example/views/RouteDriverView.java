package org.example.views;

import org.example.models.entities.RouteDriver;
import org.example.models.repositories.RouteDriverRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;

public class RouteDriverView extends JFrame {
    private RouteDriverRepository repo = RouteDriverRepository.getInstance();

    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField txtRouteId, txtDriverId;
    private JTextField txtDepartureDate, txtArrivalDate;
    private JCheckBox chkBonusStatus;
    private JTextField txtActualPayment;

    private JButton btnCreate, btnUpdate, btnDelete, btnRefresh;

    public RouteDriverView() {
        setTitle("Route Drivers");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(
                new String[]{"Route ID", "Driver ID", "Departure Date", "Arrival Date", "Bonus Status", "Actual Payment"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.add(new JLabel("Route ID:"));
        txtRouteId = new JTextField();
        formPanel.add(txtRouteId);

        formPanel.add(new JLabel("Driver ID:"));
        txtDriverId = new JTextField();
        formPanel.add(txtDriverId);

        formPanel.add(new JLabel("Departure Date (yyyy-mm-dd):"));
        txtDepartureDate = new JTextField();
        formPanel.add(txtDepartureDate);

        formPanel.add(new JLabel("Arrival Date (yyyy-mm-dd):"));
        txtArrivalDate = new JTextField();
        formPanel.add(txtArrivalDate);

        formPanel.add(new JLabel("Bonus Status:"));
        chkBonusStatus = new JCheckBox();
        formPanel.add(chkBonusStatus);

        formPanel.add(new JLabel("Actual Payment:"));
        txtActualPayment = new JTextField();
        formPanel.add(txtActualPayment);

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

        btnCreate.addActionListener(e -> createRouteDriver());
        btnUpdate.addActionListener(e -> updateRouteDriver());
        btnDelete.addActionListener(e -> deleteRouteDriver());
        btnRefresh.addActionListener(e -> loadData());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtRouteId.setText(tableModel.getValueAt(row, 0).toString());
                    txtDriverId.setText(tableModel.getValueAt(row, 1).toString());
                    txtDepartureDate.setText(tableModel.getValueAt(row, 2).toString());
                    txtArrivalDate.setText(tableModel.getValueAt(row, 3).toString());
                    chkBonusStatus.setSelected(Boolean.parseBoolean(tableModel.getValueAt(row, 4).toString()));
                    txtActualPayment.setText(tableModel.getValueAt(row, 5).toString());

                    txtRouteId.setEditable(false);
                    txtDriverId.setEditable(false);
                }
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<RouteDriver> list = repo.getAll();
        for (RouteDriver rd : list) {
            tableModel.addRow(new Object[]{
                    rd.getRouteId(),
                    rd.getDriverId(),
                    rd.getDepartureDate(),
                    rd.getArrivalDate(),
                    rd.isBonusStatus(),
                    rd.getActualPayment()
            });
        }
        txtRouteId.setEditable(true);
        txtDriverId.setEditable(true);
        txtRouteId.setText("");
        txtDriverId.setText("");
        txtDepartureDate.setText("");
        txtArrivalDate.setText("");
        chkBonusStatus.setSelected(false);
        txtActualPayment.setText("");
    }

    private void createRouteDriver() {
        try {
            int routeId = Integer.parseInt(txtRouteId.getText());
            int driverId = Integer.parseInt(txtDriverId.getText());
            java.sql.Date departureDate = java.sql.Date.valueOf(txtDepartureDate.getText());
            java.sql.Date arrivalDate = java.sql.Date.valueOf(txtArrivalDate.getText());
            boolean bonusStatus = chkBonusStatus.isSelected();
            BigDecimal actualPayment = new BigDecimal(txtActualPayment.getText());

            repo.create(new RouteDriver(routeId, driverId, departureDate.toLocalDate(), arrivalDate.toLocalDate(), bonusStatus, actualPayment));
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format: " + ex.getMessage());
        }
    }

    private void updateRouteDriver() {
        try {
            int routeId = Integer.parseInt(txtRouteId.getText());
            int driverId = Integer.parseInt(txtDriverId.getText());
            java.sql.Date departureDate = java.sql.Date.valueOf(txtDepartureDate.getText());
            java.sql.Date arrivalDate = java.sql.Date.valueOf(txtArrivalDate.getText());
            boolean bonusStatus = chkBonusStatus.isSelected();
            BigDecimal actualPayment = new BigDecimal(txtActualPayment.getText());

            repo.update(new RouteDriver(routeId, driverId, departureDate.toLocalDate(), arrivalDate.toLocalDate(), bonusStatus, actualPayment));
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format: " + ex.getMessage());
        }
    }

    private void deleteRouteDriver() {
        try {
            int routeId = Integer.parseInt(txtRouteId.getText());
            int driverId = Integer.parseInt(txtDriverId.getText());

            repo.delete(routeId, driverId);
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
        }
    }
}