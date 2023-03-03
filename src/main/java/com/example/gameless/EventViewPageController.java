package com.example.gameless;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EventViewPageController implements Initializable {

    @FXML
    public TableView<EventStudentInfo> studentInfoTable;
    @FXML
    public Label eventNameLabel;
    @FXML
    public TableColumn studentNumber;
    @FXML
    public TableColumn grade;
    @FXML
    public TableColumn present;
    @FXML
    public TableColumn firstName;
    @FXML
    public TableColumn lastName;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ObservableList<EventStudentInfo> eventStudentInfo = FXCollections.observableArrayList();
    String username;

    public void backEventButtonOnAction(ActionEvent event) throws IOException {
        //Loads the AdminEventPage.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminEventPage.fxml"));
        root = loader.load();
        //Get the EventPageController instance
        EventPageController eventPageController = loader.getController();
        eventPageController.adminUsername = username;
        eventPageController.getEvents(false);
        eventPageController.setStudent(false);
        //Get the stage from the event source and set the new scene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getStudents(String eventName) {
        //Create a new Database connection and get a connection to the database
        eventNameLabel.setText(eventName);

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        //Define the SQL query to get all students for the given event
        String getStudents = "SELECT * FROM `" + eventName + "_participants`;";

        try {
            //Define the SQL query to collect all students for the event
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getStudents);

            while (queryResult1.next()) {
                //Creates a new instance and get a connection to the database
                DatabaseConnection connectNow1 = new DatabaseConnection();
                Connection connectDb1 = connectNow1.getConnection();
                //Define the SQL query to get all students for the given event
                String getStudentInfo = "SELECT * FROM student_accounts WHERE studentNumber='" + queryResult1.getString("participantID") + "';";

                try {
                    //Create a statement and execute query to get all participants for the event
                    Statement statement = connectDb1.createStatement();
                    ResultSet queryResult = statement.executeQuery(getStudentInfo);

                    while (queryResult.next()) {
                        //Create a new EventStudentInfo object with the retrieved data and add it to the listCreate a new
                        eventStudentInfo.add(new EventStudentInfo(queryResult.getString("studentNumber"), queryResult.getString("studentFirstName"), queryResult.getString("studentLastName"), queryResult.getInt("studentGrade"), queryResult1.getBoolean("showedUp")));
                    }
                } catch (Exception e) {
                    //Print the stack trace if an exception occurs while executing the query
                    e.printStackTrace();
                    e.getCause();
                }
            }
        } catch (Exception e) {
            //Print the stack trace if an exception occurs while executing the query
            e.printStackTrace();
            e.getCause();
        }
        //Set the items of the studentInfoTable with the eventStudentInfo list
        studentInfoTable.setItems(eventStudentInfo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentNumber.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        present.setCellValueFactory(new PropertyValueFactory<>("showedUp"));
    }

    public void submitButtonOnAction(ActionEvent event){
        ObservableList<EventStudentInfo> dataListPresent = FXCollections.observableArrayList();
    }
}
