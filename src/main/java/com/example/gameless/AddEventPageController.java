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
    private Button publishEventButton;

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
        //System.out.println(tagChosen); // prints the value onto the console
    }

    //method to disable to the publish button if the inputs are invalid
    public void keyReleasedProperty(){
        String name = eventName.getText();
        String time = eventTime.getText();
        String date = eventDate.getValue().toString();
        String point = eventPoints.getText();
        String loc = eventLocation.getText();
        String desc = eventDescription.getText();
        boolean isDisabled = (name.isEmpty() || name.trim().isEmpty()) ||
                (time.isEmpty() || time.trim().isEmpty()|| (!time.matches("([01]\\d|2[0-4]):[0-5]\\d")) && !time.matches("[1-9]:[0-5]\\d")) ||
                (date.isEmpty() || date.trim().isEmpty()) ||
                (point.isEmpty() || point.trim().isEmpty() || !point.matches("0|[1-9]\\d*")) ||
                (loc.isEmpty() || loc.trim().isEmpty()) ||
                (desc.isEmpty() || desc.trim().isEmpty() || desc.length()>450);
        publishEventButton.setDisable(isDisabled);
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
        String addDB = "CREATE TABLE `fbla`.`" + eventName.getText() + "_participants` (\n" +
                "  `participantID` VARCHAR(45) NOT NULL,\n" +
                "  `showedUp` TINYINT(3) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY (`participantID`));";
        try {
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(addEvent);
            statement.execute(addDB);
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
