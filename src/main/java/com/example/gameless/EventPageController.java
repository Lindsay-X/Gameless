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
    public Label coinValue;
    @FXML
    private ChoiceBox<String> eventTagChoice;
    private String[] tag = {"All","Sports", "Art", "Theater", "Music", "Community Service", "Academic"};

    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean isStudent;
    private String studentID;
    String adminUsername;
    @FXML
    private VBox eventBoxes;

    public void initialize(URL arg0, ResourceBundle arg1){
        eventTagChoice.getItems().addAll(tag);
        eventTagChoice.setOnAction(this::getTag); //calls the method
    }

    //Method to get all events, either for student or admin
    public void getEvents(boolean isStudent) throws IOException {
        //Establish database connection
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        //Select all event IDs from events table
        String getNames = "SELECT eventID FROM events;";

        try {
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getNames);

            //Loop through each event ID
            while (queryResult1.next()) {
                int id = queryResult1.getInt("eventID");
                //Choose which FXML file to load based on user type
                String boxURL;
                if (!isStudent) {
                    boxURL = "admin/EventBox.fxml";
                } else {
                    boxURL = "student/StudentEventBox.fxml";
                }
                //Load FXML file and gets controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource(boxURL));
                root = loader.load();
                EventBoxController eventBoxController = loader.getController();
                eventBoxController.setStudentID(studentID);
                eventBoxController.initData(id, adminUsername);

                //Select all event details from events table for the current event ID
                String getEvents = "SELECT * FROM events WHERE eventID = '" + id + "';";

                try {
                    Statement statement = connectDb.createStatement();
                    ResultSet queryResult = statement.executeQuery(getEvents);

                    //Loop through each row of event details
                    while (queryResult.next()) {

                        //Set the event details in the event box controller
                        eventBoxController.eventDescriptionLabel.setText(queryResult.getString("eventDescription"));
                        eventBoxController.eventNameLabel.setText(queryResult.getString("eventName"));
                        eventBoxController.eventTimeLabel.setText(queryResult.getString("eventTime").substring(0, queryResult.getString("eventTime").length() - 3));
                        eventBoxController.eventPointsLabel.setText(queryResult.getString("eventPoints"));
                        eventBoxController.eventLocationLabel.setText(queryResult.getString("eventLocation"));
                        //If user is a student, check if they are already participating in the event
                        if (isStudent) {
                            String getIsJoin = "SELECT count(1) FROM `" + eventBoxController.eventNameLabel.getText() + "_participants` WHERE participantID='" + studentID + "'";
                            Statement statement2 = connectDb.createStatement();
                            ResultSet queryResult2 = statement2.executeQuery(getIsJoin);
                            while (queryResult2.next()) {
                                if (queryResult2.getInt(1) == 1) {
                                    eventBoxController.setButton(true);
                                } else {
                                    eventBoxController.setButton(false);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getCause();
                }
                //Add the event box to the eventBoxes
                eventBoxes.getChildren().add(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void getEvents(boolean isStudent, String tag) throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String getNames = "SELECT eventID FROM events WHERE eventTag = '" + tag + "';";

        try {
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getNames);

            while (queryResult1.next()) {
                int id = queryResult1.getInt("eventID");
                String boxURL;
                if (!isStudent) {
                    boxURL = "admin/EventBox.fxml";
                } else {
                    boxURL = "student/StudentEventBox.fxml";
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource(boxURL));
                root = loader.load();
                EventBoxController eventBoxController = loader.getController();
                eventBoxController.setStudentID(studentID);
                eventBoxController.initData(id, adminUsername);

                String getEvents = "SELECT * FROM events WHERE eventID = '" + id + "';";

                try {
                    Statement statement = connectDb.createStatement();
                    ResultSet queryResult = statement.executeQuery(getEvents);

                    while (queryResult.next()) {

                        eventBoxController.eventDescriptionLabel.setText(queryResult.getString("eventDescription"));
                        eventBoxController.eventNameLabel.setText(queryResult.getString("eventName"));
                        eventBoxController.eventTimeLabel.setText(queryResult.getString("eventTime").substring(0, queryResult.getString("eventTime").length() - 3));
                        eventBoxController.eventPointsLabel.setText(queryResult.getString("eventPoints"));
                        eventBoxController.eventLocationLabel.setText(queryResult.getString("eventLocation"));
                        if (isStudent) {
                            String getIsJoin = "SELECT count(1) FROM `" + eventBoxController.eventNameLabel.getText() + "_participants` WHERE participantID='" + studentID + "'";
                            Statement statement2 = connectDb.createStatement();
                            ResultSet queryResult2 = statement2.executeQuery(getIsJoin);
                            while (queryResult2.next()) {
                                if (queryResult2.getInt(1) == 1) {
                                    eventBoxController.setButton(true);
                                } else {
                                    eventBoxController.setButton(false);
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    e.getCause();
                }

                eventBoxes.getChildren().add(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //method to get the tag values
    public void getTag(ActionEvent event) {
        String tagChosen = eventTagChoice.getValue();
        eventBoxes.getChildren().clear();
        try {
            if (tagChosen != "All") {
                getEvents(isStudent, tagChosen);
            } else {
                getEvents(isStudent);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Button Actions
    public void backButtonOnAction(ActionEvent event) throws IOException {
        String username = adminUsername;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/adminHomePage.fxml"));
        root = loader.load();
        HomePageController homePageController = loader.getController();
        homePageController.setUsername(username);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void studentBackButtonOnAction(ActionEvent event) throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM student_accounts WHERE studentNumber = '" + studentID + "';";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    String username = studentID;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("student/StudentHomePage.fxml"));
                    root = loader.load();
                    HomePageController homePageController = loader.getController();
                    homePageController.studentDisplayName(username);
                    homePageController.getAnnouncements();
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void addEventButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminEventAddPage.fxml"));
        root = loader.load();
        AddEventPageController addEventPageController = loader.getController();
        addEventPageController.username = adminUsername;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
