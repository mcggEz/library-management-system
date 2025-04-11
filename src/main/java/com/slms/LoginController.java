// src/main/java/com/slms/LoginController.java
package com.slms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import javafx.scene.control.Label;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void switchToSecondary() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // ✅ Hardcoded credentials
        if (username.equals("admin") && password.equals("password")) {
            App.setRoot("Dashboard");  // Navigate to dashboard
        } else {
            
            errorLabel.setVisible(true);
        }
    }
}
