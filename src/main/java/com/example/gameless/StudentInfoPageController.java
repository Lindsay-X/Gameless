package com.example.gameless;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentInfoPageController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ChoiceBox<String> adminStudentGradeChoice;
    private String[] grades = {"All", "Grade 9", "Grade 10", "Grade 11", "Grade 12"};
    @FXML
    private TableView<StudentInfo> studentInfoTable;
    @FXML
    private TableColumn<StudentInfo, String> studentNumber;
    @FXML
    private TableColumn<StudentInfo, String> firstName;
    @FXML
    private TableColumn<StudentInfo, String> lastName;
    @FXML
    private TableColumn<StudentInfo, Integer> grade;
    @FXML
    private TableColumn<StudentInfo, Integer> points;
    String username;
    ObservableList<StudentInfo> list = FXCollections.observableArrayList();

    public void initialize(URL arg0, ResourceBundle arg1){
        //Add the list of grades to the adminStudentGradeChoice ComboBox
        adminStudentGradeChoice.getItems().addAll(grades);
        //Set the action to be performed when a grade is selected to the getGrade method
        adminStudentGradeChoice.setOnAction(this::getGrade);

        //Set the cell value factories for the student info table columns
        studentNumber.setCellValueFactory(new PropertyValueFactory<StudentInfo, String>("studentNumber"));
        firstName.setCellValueFactory(new PropertyValueFactory<StudentInfo, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<StudentInfo, String>("lastName"));
        grade.setCellValueFactory(new PropertyValueFactory<StudentInfo, Integer>("grade"));
        points.setCellValueFactory(new PropertyValueFactory<StudentInfo, Integer>("points"));
    }

    public void getStudents() {
        //Create a new instance of the DatabaseConnection class
        DatabaseConnection connectNow1 = new DatabaseConnection();
        Connection connectDb1 = connectNow1.getConnection();
        // SQL query to select all rows from the student_accounts table
        String getStudentInfo = "SELECT * FROM student_accounts;";

        try {
            //Create and execute statement object to execute the SQL query
            Statement statement = connectDb1.createStatement();
            ResultSet queryResult = statement.executeQuery(getStudentInfo);

            while (queryResult.next()) {
                //Use the values from the current row to create a new StudentInfo object and add it to the list
                list.add(new StudentInfo(queryResult.getString("studentNumber"), queryResult.getString("studentFirstName"), queryResult.getString("studentLastName"), queryResult.getInt("studentGrade"), queryResult.getInt("studentPoints")));
            }
        } catch (Exception e) {
            //Print the stack trace if there's an exception
            e.printStackTrace();
            e.getCause();
        }
        //Set the list of student information as the items in the studentInfoTable
        studentInfoTable.setItems(list);
    }

    //Method retrieves all student information from database
    public void getStudents(int grade) {
        //Create a new database connection
        DatabaseConnection connectNow1 = new DatabaseConnection();
        Connection connectDb1 = connectNow1.getConnection();
        //Create SQL query to get all student information from the "student_accounts" table
        String getStudentInfo = "SELECT * FROM student_accounts WHERE studentGrade=" + grade + ";";

        try {
            //Create and execute statement object to execute the SQL query
            Statement statement = connectDb1.createStatement();
            ResultSet queryResult = statement.executeQuery(getStudentInfo);

            //Loop through the result set and add each student's information to the list
            while (queryResult.next()) {
                list.add(new StudentInfo(queryResult.getString("studentNumber"), queryResult.getString("studentFirstName"), queryResult.getString("studentLastName"), queryResult.getInt("studentGrade"), queryResult.getInt("studentPoints")));
            }
        } catch (Exception e) {
            //Print the stack trace if there's an exception
            e.printStackTrace();
            e.getCause();
        }
        //Set the list of student information as the items in the studentInfoTable
        studentInfoTable.setItems(list);
    }

    public void getGrade(ActionEvent event){
        //Get the value of the selected grade from the adminStudentGradeChoice ComboBox
        String gradeChosen = adminStudentGradeChoice.getValue();
        //Clear the items in the studentInfoTable
        for ( int i = 0; i<studentInfoTable.getItems().size(); i++) {
            studentInfoTable.getItems().clear();
        }
        //If the selected grade is "All", call the getStudents method to retrieve all student information
        if (gradeChosen.equals("All")) {
            getStudents();
        } else {
            //Extract the integer value of the selected grade from the string (e.g. "Grade 9" -> 9)
            getStudents(Integer.parseInt(gradeChosen.substring(6)));
        }
    }

    public void backButtonOnAction(ActionEvent event) throws IOException {
        //Create a new FXMLLoader to load the adminHomePage.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/adminHomePage.fxml"));
        //Load file and retrieve root node
        root = loader.load();
        //Get the HomePageController instance from the loader to access its setUsername method
        HomePageController homePageController = loader.getController();
        homePageController.setUsername(username);
        //Get the current stage from the event source and set its scene to the newly loaded FXML file
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        //Show stage
        stage.show();
    }
}
