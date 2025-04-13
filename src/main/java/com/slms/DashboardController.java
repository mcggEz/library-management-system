package com.slms;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DashboardController {

    @FXML
    private VBox sideNav;

    private boolean navVisible = true;

    @FXML
    private void handleMenuToggle() {
        FadeTransition fade = new FadeTransition(Duration.millis(200), sideNav);
        if (navVisible) {
            sideNav.setVisible(false);
            sideNav.setManaged(false);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
        } else {
            sideNav.setVisible(true);
            sideNav.setManaged(true);
            fade.setFromValue(0.0);
            fade.setToValue(1.0);
        }
        fade.play();
        navVisible = !navVisible;
    }
    @FXML
    private VBox dynamicContent;

    public void initialize() {
        loadView("dashboard_view.fxml"); // Load the default view
    }
    
    private void loadView(String fxml) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/com/slms/" + fxml));
            dynamicContent.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    private void handleDashboard() {
        loadView("dashboard_view.fxml");
    }

    @FXML
    private void handleStaffs() {
        loadView("staffs.fxml");
    }

    @FXML
    private void handleMembers() {
        loadView("members.fxml");
    }

    @FXML
    private void handleBooks() {
        loadView("books.fxml");
    }

    @FXML
    private void handleBorrowedBooks() {
        loadView("borrowed_books.fxml");
    }

    @FXML
    private void handleHelp() {
        loadView("help.fxml");
    }

    @FXML
    private void handleSettings() {
        loadView("settings.fxml");
    }
@FXML
private void handleLogout() throws IOException {
    App.setRoot("Login"); // Navigate back to the login screen
}

@FXML
private void openForms() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/slms/AttendanceForm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Attendance Form");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}

