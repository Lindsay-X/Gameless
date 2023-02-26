package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomePageController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String username;
    @FXML
    private Button studentProfileButton;
    @FXML
    private Button adminAnnouncementsButton;
    @FXML
    private Label adminEventNameLabel;
    @FXML
    private Label adminEventDescriptionLabel;
    @FXML
    private Label adminEventTimeLabel;
    @FXML
    private Label adminEventLocationLabel;
    @FXML
    private Label adminEventPointsLabel;

    public void studentDisplayName(String username) {
        this.username = username;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String getName = "SELECT studentFirstName, studentLastName FROM student_accounts WHERE studentNumber = '" + username + "';";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(getName);
            String name = "";

            while(queryResult.next()) {
                name += queryResult.getString("studentFirstName") + " " + queryResult.getString("studentLastName");
            }

            studentProfileButton.setText(name);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void studentProfileButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("student/StudentProfilePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void studentInfoButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminStudentInfoPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void adminEventButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminEventPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
