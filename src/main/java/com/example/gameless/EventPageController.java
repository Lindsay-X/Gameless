package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventPageController implements Initializable{
    @FXML
    private ChoiceBox<String> eventTagChoice;
    private String[] tag = {"Sports", "Art", "Theater", "Music", "Community Service", "Academic"};

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox eventBoxes;

    public void initialize(URL arg0, ResourceBundle arg1){
        eventTagChoice.getItems().addAll(tag);
        eventTagChoice.setOnAction(this::getTag); //calls the method
    }

    public void getEvents() throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String getNames = "SELECT eventID FROM events;";

        try {
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getNames);

            while (queryResult1.next()) {
                int id = queryResult1.getInt("eventID");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/EventBox.fxml"));
                root = loader.load();
                EventBoxController eventBoxController = loader.getController();
                eventBoxController.initData(id);


                String getEvents = "SELECT * FROM events WHERE eventID = '" + id + "';";

                try {
                    Statement statement = connectDb.createStatement();
                    ResultSet queryResult = statement.executeQuery(getEvents);

                    while (queryResult.next()) {
                        eventBoxController.eventDescriptionLabel.setText(queryResult.getString("eventDescription"));
                        eventBoxController.eventNameLabel.setText(queryResult.getString("eventName"));
                        eventBoxController.eventTimeLabel.setText(queryResult.getString("eventTime"));
                        eventBoxController.eventPointsLabel.setText(queryResult.getString("eventPoints"));
                        eventBoxController.eventLocationLabel.setText(queryResult.getString("eventLocation"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    e.getCause();
                }

                eventBoxes.getChildren().add(0, root);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //method to get the tag values
    public void getTag(ActionEvent event) {
        String tagChosen = eventTagChoice.getValue();
        System.out.println(tagChosen); // prints the value onto the console
    }

    //Button Actions
    public void backButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminHomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addEventButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminEventAddPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
