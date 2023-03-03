package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EventBoxController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    int id;
    private String studentID;
    String username;
    @FXML
    Label eventDescriptionLabel;
    @FXML
    Label eventNameLabel;
    @FXML
    Label eventTimeLabel;
    @FXML
    Label eventLocationLabel;
    @FXML
    Label eventPointsLabel;
    @FXML
    Button joinEventButton;

    public void joinEventButtonOnAction(ActionEvent event) {
        //Set the button appearance and text for when the student is signed up for the event
        setButton(true);
        //Set the button action to remove the student's ID from the database when clicked
        joinEventButton.setOnAction(this::leaveEventButtonOnAction);
        //Get a database connection and insert the student's ID into the event's participants table
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String addEvent = "INSERT INTO `" + eventNameLabel.getText() + "_participants` (participantID) VALUES ('" + studentID + "')";
        try {
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(addEvent);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void leaveEventButtonOnAction(ActionEvent event) {
        //Set the button appearance and text for when the student is not signed up for the event
        setButton(false);
        //Set the button action to sign up the student for the event when clicked
        joinEventButton.setOnAction(this::joinEventButtonOnAction);
        //Get a database connection and remove the student's ID from the event's participants table
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String addEvent = "DELETE FROM `" + eventNameLabel.getText() + "_participants` WHERE participantID='" + studentID + "'";
        try {
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(addEvent);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void setButton(boolean isJoin) {
        if (!isJoin) {
            joinEventButton.setText("Join");
            joinEventButton.setStyle("-fx-background-color: #ABFFC9; -fx-background-radius: 7.5;");
            joinEventButton.setOnAction(this::joinEventButtonOnAction);
        } else {
            joinEventButton.setText("Leave");
            joinEventButton.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 7.5;");
            joinEventButton.setOnAction(this::leaveEventButtonOnAction);
        }
    }

    public void viewEventButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminEventViewPage.fxml"));
        root = loader.load();
        EventViewPageController eventviewPageController = loader.getController();
        eventviewPageController.getStudents(eventNameLabel.getText());
        eventviewPageController.username = username;
        eventviewPageController.points = Integer.parseInt(eventPointsLabel.getText());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    void initData(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
