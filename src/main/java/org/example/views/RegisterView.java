package org.example.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controllers.AuthController;

public class RegisterView {
    private AuthController authController = new AuthController();

    public void show(Stage stage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("admin", "dispatcher", "driver");
        roleBox.setPromptText("Role");

        Button registerButton = new Button("Register");
        Label messageLabel = new Label();

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleBox.getValue();
            boolean success = authController.register(username, password, role);
            messageLabel.setText(success ? "Registered!" : "Username already exists.");
        });

        layout.getChildren().addAll(usernameField, passwordField, roleBox, registerButton, messageLabel);
        stage.setScene(new Scene(layout, 300, 250));
        stage.setTitle("Register");
        stage.show();
    }
}
