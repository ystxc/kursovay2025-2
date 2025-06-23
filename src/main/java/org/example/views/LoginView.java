package org.example.views;

import org.example.controllers.AuthController;
import org.example.models.entities.User;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private AuthController authController = new AuthController();

    private JTextField txtUsernameLogin = new JTextField(15);
    private JPasswordField txtPasswordLogin = new JPasswordField(15);
    private JButton btnLogin = new JButton("Login");

    private JTextField txtUsernameRegister = new JTextField(15);
    private JPasswordField txtPasswordRegister = new JPasswordField(15);
    private JButton btnRegister = new JButton("Register");

    public LoginView() {
        setTitle("Login / Register");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Вход
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(txtUsernameLogin);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(txtPasswordLogin);
        loginPanel.add(new JLabel());
        loginPanel.add(btnLogin);
        tabbedPane.addTab("Login", loginPanel);

        // Регистрация
        JPanel registerPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        registerPanel.add(new JLabel("Username:"));
        registerPanel.add(txtUsernameRegister);
        registerPanel.add(new JLabel("Password:"));
        registerPanel.add(txtPasswordRegister);
        registerPanel.add(new JLabel());
        registerPanel.add(btnRegister);
        tabbedPane.addTab("Register", registerPanel);

        add(tabbedPane);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> register());
    }

    private void login() {
        String username = txtUsernameLogin.getText().trim();
        String password = new String(txtPasswordLogin.getPassword());

        User user = authController.login(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
            return;
        }

        JOptionPane.showMessageDialog(this, "Welcome, " + user.getUsername() + "! Role: " + user.getRole());

        this.dispose(); // закрыть окно логина

        // В зависимости от роли открыть разный функционал
        if ("admin".equalsIgnoreCase(user.getRole())) {
            new AdminMainView(user).setVisible(true);
        } else {
            new UserMainView(user).setVisible(true);
        }
    }

    private void register() {
        String username = txtUsernameRegister.getText().trim();
        String password = new String(txtPasswordRegister.getPassword());

        if (!(username.startsWith("admin") || username.startsWith("user"))) {
            JOptionPane.showMessageDialog(this, "Username must start with 'admin' or 'user'");
            return;
        }

        String role = username.startsWith("admin") ? "admin" : "user";

        boolean success = authController.register(username, password, role);
        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful. Please login.");
        } else {
            JOptionPane.showMessageDialog(this, "User already exists.");
        }
    }
}
