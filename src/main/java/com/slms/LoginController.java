package com.slms;

import com.slms.util.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void switchToSecondary() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate user credentials from the database
        if (validateUserCredentials(username, password)) {
            try {
                App.setRoot("Dashboard");  // Navigate to dashboard
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setVisible(true);  // Show error if login fails
        }
    }

    private boolean validateUserCredentials(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If user exists, return true
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
