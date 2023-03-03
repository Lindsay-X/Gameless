package com.example.gameless;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin/AdminLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);
        //Sets title of stage
        stage.setTitle("Spirit Tracker");
        //Sets scene of stage
        stage.setScene(scene);
        //lock stage size
        stage.setResizable(false);
        //Shows stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}