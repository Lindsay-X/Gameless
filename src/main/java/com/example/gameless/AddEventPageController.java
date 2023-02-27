package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddEventPageController implements Initializable {
    @FXML
    private ChoiceBox<String> eventTagChoice;
    private String[] tag = {"Sports", "Art", "Theater", "Music", "Community Service", "Academic"};

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField eventName;
    @FXML
    private TextField eventTime;
    @FXML
    private DatePicker eventDate;
    @FXML
    private TextField eventPoints;
    @FXML
    private TextField eventLocation;
    @FXML
    private TextArea eventDescription;

    public void initialize(URL arg0, ResourceBundle arg1){
        eventTagChoice.getItems().addAll(tag);
        eventTagChoice.setOnAction(this::getTag); //calls the method
    }

    //method to get the tage value
    public void getTag(ActionEvent event) {
        String tagChosen = eventTagChoice.getValue();
        //System.out.println(tagChosen); // prints the value onto the console
    }

    public void backEventButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminEventPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void publishButtonOnAction(ActionEvent event) throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String addEvent = "INSERT INTO events (eventName, eventDescription, eventLocation, eventTime, eventTag, eventPoints) VALUES ('" + eventName.getText() + "', '" + eventDescription.getText() + "', '" + eventLocation.getText() + "', '" + eventDate.getValue().toString() + " " + eventTime.getText() + ":00', '" + eventTagChoice.getValue() + "', '" + eventPoints.getText() + "')";

        try {
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(addEvent);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminEventPage.fxml"));
        root = loader.load();
        EventPageController eventPageController = loader.getController();
        eventPageController.getEvents(false);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
