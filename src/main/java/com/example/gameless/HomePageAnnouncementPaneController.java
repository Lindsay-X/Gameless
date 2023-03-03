package com.example.gameless;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomePageAnnouncementPaneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Label homePageAnnouncementLabel;

    // Method that initializes the HomePageAnnouncementLabel with the teacher name and message.
    void initData(String teacherName, String msg) {
        homePageAnnouncementLabel.setText(teacherName + "\n" + msg);
    }
}
