package com.example.gameless;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin/AdminHomePage.fxml"));
        // Create a new scene object with the loaded FXML file and set its dimensions
=======
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("student/studentLogin.fxml"));
>>>>>>> 42572a58659f29c4cc8a975adb4f4d93d996359e
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);
        //Sets title of stage
        stage.setTitle("Spirit Tracker");
        //Sets scene of stage
        stage.setScene(scene);
        //Shows stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}