package org.example.views;

import org.example.models.entities.User;
import org.example.models.repositories.CompanyRepository;
import org.example.models.repositories.DriverRepository;
import org.example.models.repositories.RouteDriverRepository;
import org.example.models.repositories.RouteRepository;

import javax.swing.*;
import java.awt.*;

public class AdminMainView extends JFrame {
    private final User user;

    public AdminMainView(User user) {
        this.user = user;
        setTitle("Admin Panel - " + user.getUsername());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));

        JButton btnDrivers = new JButton("Manage Drivers");
        JButton btnCompanies = new JButton("Manage Companies");
        JButton btnRoutes = new JButton("Manage Routes");
        JButton btnRouteDrivers = new JButton("Manage Route-Drivers");
        JButton btnLogout = new JButton("Logout");

        panel.add(btnDrivers);
        panel.add(btnCompanies);
        panel.add(btnRoutes);
        panel.add(btnRouteDrivers);
        panel.add(btnLogout);

        add(panel);

        btnDrivers.addActionListener(e -> {
            new DriverView().setVisible(true);
        });

        btnCompanies.addActionListener(e -> {
            new CompanyView().setVisible(true);
        });

        btnRoutes.addActionListener(e -> {
            new RouteView().setVisible(true);
        });

        btnRouteDrivers.addActionListener(e -> {
            new RouteDriverView().setVisible(true);
        });


        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginView().setVisible(true);
        });
    }
}
