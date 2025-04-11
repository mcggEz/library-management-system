package com.slms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML
        Parent root = loadFXML("Login");

        // Create a larger Scene
        scene = new Scene(root, 800, 600); // 🔧 Adjusted from 640x480

        stage.setTitle("Sacramento Library Management System"); // Optional: Set window title
        stage.setScene(scene);
        

        // Optional: prevent window resizing
        // stage.setResizable(false);

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
