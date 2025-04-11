package com.slms;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardController {

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("Login");
    } 
    @FXML
private Label booksLabel;
@FXML
private VBox menuBox;

@FXML
private void showMenu() {
    menuBox.setVisible(!menuBox.isVisible()); // toggles the whole group
}

}