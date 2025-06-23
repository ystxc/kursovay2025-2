package org.example.views;

import org.example.models.entities.Company;
import org.example.models.repositories.CompanyRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CompanyViewReadOnly extends JFrame {
    private CompanyRepository repo = CompanyRepository.getInstance();

    private DefaultTableModel tableModel;
    private JTable table;

    public CompanyViewReadOnly() {
        setTitle("Companies (Read-Only)");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Заголовки таблицы
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name",}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Company> list = repo.getAll();
        for (Company c : list) {
            tableModel.addRow(new Object[]{
                    c.getId(),
                    c.getName()
            });
        }
    }
}
