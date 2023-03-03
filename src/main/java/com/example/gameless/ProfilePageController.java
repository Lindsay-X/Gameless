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
        //Load the StudentHomePage.fxml file using an FXMLLoader object
        root = FXMLLoader.load(getClass().getResource("student/StudentHomePage.fxml"));
        //Get the Stage object and set it as the stage for the newly loaded scene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //Creates a new scene object
        String username = studentNumberLabel.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student/StudentHomePage.fxml"));
        root = loader.load();
        HomePageController homePageController = loader.getController();
        homePageController.studentDisplayName(username);
        homePageController.getAnnouncements();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        //Shows stage
        stage.show();
    }

    public void displayStudentInfo(String username, String name, int points, int grade) {
        //Sets the label to the student information
        studentGradeLabel.setText("" + grade);
        studentNameLabel.setText(name);
        studentNumberLabel.setText(username);
        studentPointsLabel.setText(points + "");
    }
}
