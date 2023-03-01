package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
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

    @FXML
    private Button publishEventButton = new Button();

    @FXML
    private Label characterCount;

    public void initialize(URL arg0, ResourceBundle arg1){
        eventTagChoice.getItems().addAll(tag);
        eventTagChoice.setOnAction(this::getTag); //calls the method
        publishEventButton.setDisable(true);
        characterCount.textProperty().bind(eventDescription.textProperty()
                .length()
                .asString("Character Count: %d"));
    }

    //method to get the tag value
    public void getTag(ActionEvent event) {
        String tagChosen = eventTagChoice.getValue();
    }

    //method to disable to the publish button if the inputs are invalid
    public void keyReleasedProperty(){
        //Extracting the value of the variables
        String name = eventName.getText();
        String time = eventTime.getText();
        LocalDate date = eventDate.getValue();
        String point = eventPoints.getText();
        String loc = eventLocation.getText();
        String desc = eventDescription.getText();
        //Checks if all the inputs are valid
        boolean isDisabled = (name.isEmpty() || name.trim().isEmpty()) ||
                (time.isEmpty() || time.trim().isEmpty()|| (!time.matches("([01]\\d|2[0-4]):[0-5]\\d")) && !time.matches("[1-9]:[0-5]\\d")) ||
                (date != null) ||
                (point.isEmpty() || point.trim().isEmpty() || !point.matches("0|[1-9]\\d*")) ||
                (loc.isEmpty() || loc.trim().isEmpty()) ||
                (desc.isEmpty() || desc.trim().isEmpty() || desc.length()>450);
        //Property to enable or disable the button based on the validation result
        publishEventButton.setDisable(isDisabled);
    }

    public void backEventButtonOnAction(ActionEvent event) throws IOException {
        //Load the FXML file for the admin event page and set it as the root object
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminEventPage.fxml"));
        root = loader.load();
        //Load the events onto the event page
        EventPageController eventPageController = loader.getController();
        eventPageController.getEvents(false);
        eventPageController.setStudent(false);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //Get the stage from the event source and set the new scene to the root object
        scene = new Scene(root);
        stage.setScene(scene);
        //Show the updated stage with the new scene
        stage.show();
    }

    public void publishButtonOnAction(ActionEvent event) throws IOException {
        //Connect to database
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        //Define SQL statement to add new event to database
        String addEvent = "INSERT INTO events (eventName, eventDescription, eventLocation, eventTime, eventTag, eventPoints) VALUES ('" + eventName.getText() + "', '" + eventDescription.getText() + "', '" + eventLocation.getText() + "', '" + eventDate.getValue().toString() + " " + eventTime.getText() + ":00', '" + eventTagChoice.getValue() + "', '" + eventPoints.getText() + "')";
        String addDB = "CREATE TABLE `fbla`.`" + eventName.getText() + "_participants` (\n" +
                "  `participantID` VARCHAR(45) NOT NULL,\n" +
                "  `showedUp` TINYINT(3) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY (`participantID`));";
        try {
            //Execute SQL statement
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(addEvent);
            statement.execute(addDB);
        } catch (Exception e) {
            //Print errors that occur
            e.printStackTrace();
            e.getCause();
        }
        //Load admin event page and set it as the root object
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminEventPage.fxml"));
        root = loader.load();
        //Get the class from the FXML loader and calls a method to update events
        EventPageController eventPageController = loader.getController();
        eventPageController.getEvents(false);
        //Set the new scene to the root object and display the updated stage
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
