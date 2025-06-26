package org.example.views;

import org.example.controllers.AuthController;
import org.example.models.entities.User;

import javax.swing.*;
import java.awt.*;

public class EditProfileView extends JFrame {
    private final User user;
    private final AuthController authController = new AuthController();

    private JTextField txtOldUsername = new JTextField(20);
    private JPasswordField txtOldPassword = new JPasswordField(20);

    private JTextField txtNewUsername1 = new JTextField(20);
    private JTextField txtNewUsername2 = new JTextField(20);

    private JPasswordField txtNewPassword1 = new JPasswordField(20);
    private JPasswordField txtNewPassword2 = new JPasswordField(20);

    private JButton btnSave = new JButton("Сохранить");

    public EditProfileView(User user) {
        this.user = user;
        setTitle("logistics++ - Редактирование профиля");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        // Главный контейнер
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Заголовок
        JLabel titleLabel = new JLabel("Редактирование профиля", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Панель с полями ввода
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 15, 15));
        formPanel.setBackground(Color.WHITE);

        // Настройка стилей для меток
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        addFormField(formPanel, "Текущий логин:", txtOldUsername, labelFont);
        addFormField(formPanel, "Текущий пароль:", txtOldPassword, labelFont);
        addFormField(formPanel, "Новый логин:", txtNewUsername1, labelFont);
        addFormField(formPanel, "Повторите новый логин:", txtNewUsername2, labelFont);
        addFormField(formPanel, "Новый пароль:", txtNewPassword1, labelFont);
        addFormField(formPanel, "Повторите новый пароль:", txtNewPassword2, labelFont);

        // Кнопка сохранения
        btnSave.setBackground(new Color(176, 224, 230));
        btnSave.setForeground(Color.BLACK);
        btnSave.setFocusPainted(false);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 25, 8, 25)
        ));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnSave);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        btnSave.addActionListener(e -> saveProfile());
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, Font font) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label);
        panel.add(field);
    }

    private void saveProfile() {
        String oldUsername = txtOldUsername.getText().trim();
        String oldPassword = new String(txtOldPassword.getPassword());

        String newUsername1 = txtNewUsername1.getText().trim();
        String newUsername2 = txtNewUsername2.getText().trim();

        String newPassword1 = new String(txtNewPassword1.getPassword());
        String newPassword2 = new String(txtNewPassword2.getPassword());

        if (oldUsername.isEmpty() || oldPassword.isEmpty() ||
                newUsername1.isEmpty() || newUsername2.isEmpty() ||
                newPassword1.isEmpty() || newPassword2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, заполните все поля");
            return;
        }

        if (!newUsername1.equals(newUsername2)) {
            JOptionPane.showMessageDialog(this, "Новые логины не совпадают");
            return;
        }

        if (!newPassword1.equals(newPassword2)) {
            JOptionPane.showMessageDialog(this, "Новые пароли не совпадают");
            return;
        }

        User verifiedUser = authController.login(oldUsername, oldPassword);
        if (verifiedUser == null || verifiedUser.getId() != user.getId()) {
            JOptionPane.showMessageDialog(this, "Текущий логин или пароль неверны");
            return;
        }

        boolean success = authController.updateUserProfile(
                user.getId(),
                oldPassword,
                newUsername1,
                newPassword1
        );

        if (success) {
            JOptionPane.showMessageDialog(this, "Профиль успешно обновлен");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Ошибка при обновлении профиля (возможно, логин уже занят)");
        }
    }
}