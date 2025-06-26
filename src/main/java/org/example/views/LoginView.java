package org.example.views;

import org.example.controllers.AuthController;
import org.example.models.entities.User;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private AuthController authController = new AuthController();

    private JTextField txtUsernameLogin = new JTextField(20);
    private JPasswordField txtPasswordLogin = new JPasswordField(20);
    private JButton btnLogin = new JButton("Вход");

    private JTextField txtUsernameRegister = new JTextField(20);
    private JPasswordField txtPasswordRegister = new JPasswordField(20);
    private JButton btnRegister = new JButton("Регистрация");

    public LoginView() {
        setTitle("logistics++ - Авторизация");
        setSize(700, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Название компании
        JLabel companyLabel = new JLabel("LOGISTICS++", SwingConstants.CENTER);
        companyLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        companyLabel.setForeground(new Color(0, 100, 200));
        companyLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));
        add(companyLabel, BorderLayout.NORTH);

        // Подзаголовок
        JLabel subtitleLabel = new JLabel("Информационная система для организации грузовых перевозок", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(80, 80, 80));
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 30, 20));
        add(subtitleLabel, BorderLayout.CENTER);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(240, 240, 240));
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Стилизация вкладок
        UIManager.put("TabbedPane.selected", new Color(176, 224, 230));
        SwingUtilities.updateComponentTreeUI(tabbedPane);

        // Вход
        JPanel loginPanel = createAuthPanel(txtUsernameLogin, txtPasswordLogin, btnLogin);
        tabbedPane.addTab("Вход", loginPanel);

        // Регистрация
        JPanel registerPanel = createAuthPanel(txtUsernameRegister, txtPasswordRegister, btnRegister);
        tabbedPane.addTab("Регистрация", registerPanel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 50, 80));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(tabbedPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> register());
    }

    private JPanel createAuthPanel(JTextField usernameField, JPasswordField passwordField, JButton button) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;

        // Поля ввода
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Логин:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Пароль:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(passwordField, gbc);

        // Кнопка
        styleMainButton(button);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(button, gbc);

        return panel;
    }

    private void styleMainButton(JButton button) {
        button.setBackground(new Color(176, 224, 230));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 200, 220)),
                BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void login() {
        String username = txtUsernameLogin.getText().trim();
        String password = new String(txtPasswordLogin.getPassword());

        User user = authController.login(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Неверный логин или пароль");
            return;
        }

        JOptionPane.showMessageDialog(this, "Добро пожаловать, " + user.getUsername() + "! Роль: " +
                ("admin".equalsIgnoreCase(user.getRole()) ? "Администратор" : "Пользователь"));

        this.dispose();

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
            JOptionPane.showMessageDialog(this, "Логин должен начинаться с 'admin' или 'user'");
            return;
        }

        String role = username.startsWith("admin") ? "admin" : "user";

        boolean success = authController.register(username, password, role);
        if (success) {
            JOptionPane.showMessageDialog(this, "Регистрация успешна. Пожалуйста, войдите.");
        } else {
            JOptionPane.showMessageDialog(this, "Пользователь уже существует.");
        }
    }
}