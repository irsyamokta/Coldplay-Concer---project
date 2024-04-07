package com.project;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage mainStage) {
        try {
            Parent source = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
            Scene newScene = new Scene(source);
            mainStage.setTitle("Login");
            mainStage.setScene(newScene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}