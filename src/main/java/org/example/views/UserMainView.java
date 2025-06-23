package org.example.views;

import org.example.models.entities.User;

import javax.swing.*;
import java.awt.*;

public class UserMainView extends JFrame {
    private User user;

    public UserMainView(User user) {
        this.user = user;
        setTitle("User Panel - " + user.getUsername());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5)); // добавлена кнопка просмотра маршрутов-водителей
        JButton btnViewDrivers = new JButton("View Drivers");
        JButton btnViewCompanies = new JButton("View Companies");
        JButton btnViewRouteDrivers = new JButton("View Route-Drivers"); // новая кнопка
        JButton btnLogout = new JButton("Logout");

        panel.add(btnViewDrivers);
        panel.add(btnViewCompanies);
        panel.add(btnViewRouteDrivers);
        panel.add(btnLogout);

        add(panel);

        btnViewDrivers.addActionListener(e -> {
            new DriverViewReadOnly().setVisible(true);
        });

        btnViewCompanies.addActionListener(e -> {
            new CompanyViewReadOnly().setVisible(true);
        });

        btnViewRouteDrivers.addActionListener(e -> {
            new RouteDriverViewReadOnly().setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginView().setVisible(true);
        });
    }
}
