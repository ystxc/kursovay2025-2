package org.example.views;

import org.example.models.entities.User;

import javax.swing.*;
import java.awt.*;

public class UserMainView extends JFrame {
    private User user;

    public UserMainView(User user) {
        this.user = user;
        setTitle("logistics++ - Пользовательская панель");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        // Главная панель
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Заголовок
        JLabel titleLabel = new JLabel("Добро пожаловать, " + user.getUsername());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Панель с основными кнопками
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnViewDrivers = createMenuButton("Просмотр водителей");
        JButton btnViewCompanies = createMenuButton("Просмотр компаний");
        JButton btnViewRoute = createMenuButton("Просмотр маршрутов");
        JButton btnViewRouteDrivers = createMenuButton("Просмотр назначений");

        buttonPanel.add(btnViewDrivers);
        buttonPanel.add(btnViewCompanies);
        buttonPanel.add(btnViewRoute);
        buttonPanel.add(btnViewRouteDrivers);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Нижняя панель с кнопками
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton btnLogout = createActionButton("Выйти");
        JButton btnEditProfile = createActionButton("Редактировать профиль");

        bottomPanel.add(btnLogout);
        bottomPanel.add(btnEditProfile);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Обработчики событий
        btnViewDrivers.addActionListener(e -> {
            new DriverViewReadOnly().setVisible(true);
        });

        btnViewCompanies.addActionListener(e -> {
            new CompanyViewReadOnly().setVisible(true);
        });

        btnViewRoute.addActionListener(e -> {
            new RouteViewReadOnly().setVisible(true);
        });

        btnViewRouteDrivers.addActionListener(e -> {
            new RouteDriverViewReadOnly().setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginView().setVisible(true);
        });

        btnEditProfile.addActionListener(e -> {
            new EditProfileView(user).setVisible(true);
        });
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(176, 224, 230));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 200, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setMargin(new Insets(5, 10, 5, 10));
        return button;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(200, 230, 240));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 210, 230)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return button;
    }
}