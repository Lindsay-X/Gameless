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
        setButton(true);
        joinEventButton.setOnAction(this::leaveEventButtonOnAction);
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
        setButton(false);
        joinEventButton.setOnAction(this::joinEventButtonOnAction);
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
        } else {
            joinEventButton.setText("Leave");
            joinEventButton.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 7.5;");
        }
    }

    public void viewEventButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminEventViewPage.fxml"));
        root = loader.load();
        EventViewPageController eventviewPageController = loader.getController();
        eventviewPageController.getStudents(eventNameLabel.getText());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    void initData(int id) {
        this.id = id;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
