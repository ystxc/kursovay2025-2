package org.example.views;

import org.example.models.entities.Route;
import org.example.models.repositories.RouteRepository;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class RouteViewReadOnly extends BaseView<Route> {

    public RouteViewReadOnly() {
        super("logistics++ - Маршруты (просмотр)",
                new String[]{"ID", "Название маршрута", "Примерное расстояние", "Базовая оплата", "ID Компании"},
                RouteRepository.getInstance());

        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        // Настройка таблицы
        table.setSelectionBackground(new Color(230, 242, 255));
        table.setGridColor(new Color(220, 220, 220));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);

        // заголовок таблицы
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(176, 224, 230)); // Голубой фон заголовка
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        JLabel titleLabel = new JLabel("Список маршрутов (режим просмотра)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);


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
        List<Route> routes = repository.getAll();
        for (Route r : routes) {
            tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getName(),
                    String.format("%.2f км", r.getEstimatedDistance()),
                    String.format("%.2f ₽", r.getBasePayment()),
                    r.getCompanyId()
            });
        }
    }

    @Override
    protected void initActions() {
        btnRefresh.addActionListener(e -> loadData());
    }
}