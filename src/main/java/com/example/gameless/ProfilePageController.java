package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfilePageController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Label studentNameLabel;
    @FXML
    Label studentGradeLabel;
    @FXML
    Label studentNumberLabel;
    @FXML
    Label studentPointsLabel;

    public void studentBackButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("student/StudentHomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayStudentInfo(String username, String name, int points, int grade) {
        studentGradeLabel.setText("" + grade);
        studentNameLabel.setText(name);
        studentNumberLabel.setText(username);
        studentPointsLabel.setText(points + "");
    }
}
