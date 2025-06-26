package org.example.views;

import org.example.models.entities.RouteDriver;
import org.example.models.repositories.RouteDriverRepository;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class RouteDriverViewReadOnly extends BaseView<RouteDriver> {

    public RouteDriverViewReadOnly() {
        super("logistics++ - Маршруты-Водители (просмотр)",
                new String[]{"ID Маршрута", "ID Водителя", "Дата отправления", "Дата прибытия", "Статус премии", "Выплата"},
                RouteDriverRepository.getInstance());

        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        // Настройка таблицы
        table.setSelectionBackground(new Color(230, 242, 255));
        table.setGridColor(new Color(220, 220, 220));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);

        // Заголовок таблицы
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(176, 224, 230));
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // ненужные элементы
        btnCreate.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        formPanel.setVisible(false);

        // кнопка обновления
        btnRefresh.setText("Обновить");
        btnRefresh.setBackground(new Color(176, 224, 230));
        btnRefresh.setForeground(Color.BLACK);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 25, 8, 25)
        ));

        // новая компоновка
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Панель с кнопкой обновления
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnRefresh);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        loadData();
    }

    @Override
    protected void initForm() {

    }

    @Override
    protected void fillFormFromTable(int row) {

    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        List<RouteDriver> routeDrivers = repository.getAll();
        for (RouteDriver rd : routeDrivers) {
            tableModel.addRow(new Object[]{
                    rd.getRouteId(),
                    rd.getDriverId(),
                    rd.getDepartureDate(),
                    rd.getArrivalDate(),
                    rd.isBonusStatus() ? "Да" : "Нет",
                    rd.getActualPayment()
            });
        }
    }

    @Override
    protected void initActions() {
        btnRefresh.addActionListener(e -> loadData());
    }
}