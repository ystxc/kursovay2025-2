package org.example.views;

import org.example.models.entities.Route;
import org.example.models.repositories.RouteRepository;
import org.example.views.BaseView;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class RouteView extends BaseView<Route> {
    private RouteRepository repo = RouteRepository.getInstance();

    private JTextField txtId, txtName, txtDistance, txtPayment, txtCompanyId;

    public RouteView() {
        super("Routes", new String[]{"ID", "Name", "Estimated Distance", "Base Payment", "Company ID"}, RouteRepository.getInstance());
    }

    @Override
    protected void initForm() {
        // Используем JPanel с GridLayout для формы, чтобы было аккуратно
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        txtId = new JTextField();
        txtName = new JTextField();
        txtDistance = new JTextField();
        txtPayment = new JTextField();
        txtCompanyId = new JTextField();

        form.add(new JLabel("ID:"));
        form.add(txtId);
        form.add(new JLabel("Name:"));
        form.add(txtName);
        form.add(new JLabel("Estimated Distance:"));
        form.add(txtDistance);
        form.add(new JLabel("Base Payment:"));
        form.add(txtPayment);
        form.add(new JLabel("Company ID:"));
        form.add(txtCompanyId);

        add(form, BorderLayout.NORTH);
    }

    @Override
    protected void fillFormFromTable(int row) {
        txtId.setText(tableModel.getValueAt(row, 0).toString());
        txtName.setText(tableModel.getValueAt(row, 1).toString());
        txtDistance.setText(tableModel.getValueAt(row, 2).toString());
        txtPayment.setText(tableModel.getValueAt(row, 3).toString());
        txtCompanyId.setText(tableModel.getValueAt(row, 4).toString());

        // При редактировании ID обычно нельзя менять
        txtId.setEditable(false);
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        for (Route r : repo.getAll()) {
            tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getName(),
                    r.getEstimatedDistance(),
                    r.getBasePayment(),
                    r.getCompanyId()
            });
        }
        // Очистка формы и установка editable
        txtId.setText("");
        txtId.setEditable(true);
        txtName.setText("");
        txtDistance.setText("");
        txtPayment.setText("");
        txtCompanyId.setText("");
    }

    @Override
    protected void initActions() {
        btnCreate.addActionListener(e -> {
            try {
                Route route = new Route(
                        Integer.parseInt(txtId.getText()),
                        txtName.getText(),
                        new BigDecimal(txtDistance.getText()),
                        new BigDecimal(txtPayment.getText()),
                        Integer.parseInt(txtCompanyId.getText())
                );
                repo.create(route);
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID, Distance, Payment, and Company ID.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error creating Route: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                Route route = new Route(
                        Integer.parseInt(txtId.getText()),
                        txtName.getText(),
                        new BigDecimal(txtDistance.getText()),
                        new BigDecimal(txtPayment.getText()),
                        Integer.parseInt(txtCompanyId.getText())
                );
                repo.update(route);
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID, Distance, Payment, and Company ID.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating Route: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                repo.delete(id);
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Route ID to delete.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting Route: " + ex.getMessage());
            }
        });
    }
}
