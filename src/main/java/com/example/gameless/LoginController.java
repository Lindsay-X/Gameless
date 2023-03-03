package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField studentUsernameField;
    @FXML
    private TextField studentPasswordField;
    @FXML
    private Label studentLoginMessageLabel;
    @FXML
    private TextField adminUsernameField;
    @FXML
    private TextField adminPasswordField;
    @FXML
    private Label adminLoginMessageLabel;


    public void studentAdminButtonOnAction(ActionEvent event) throws IOException {
        //Load the AdminLogin.fxml file into the root node
        root = FXMLLoader.load(getClass().getResource("admin/AdminLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        //Show stage
        stage.show();
    }

    public void adminStudentButtonOnAction(ActionEvent event) throws IOException {
        //Load the StudentLogin.fxml file into the root node
        Parent root = FXMLLoader.load(getClass().getResource("student/StudentLogin.fxml"));
        //Get the current stage and set the scene to the root node
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        //Show stage
        stage.show();
    }

    public void studentLoginButtonOnAction(ActionEvent event) throws IOException{
        //Check if username or password field is blank
        if (!studentUsernameField.getText().isBlank() && !studentPasswordField.getText().isBlank()) {
            //If credentials are not blank, validates student's login credentials
            studentValidateLogin(event);
        } else {
            studentLoginMessageLabel.setText("Please enter your student number and/or password.");
        }
    }

    public void adminLoginButtonOnAction(ActionEvent event) {
        //Check if username or password field is blank
        if (!adminUsernameField.getText().isBlank() && !adminPasswordField.getText().isBlank()) {
            //If credentials are not blank, validates admin's login credentials
            adminValidateLogin(event);
        } else {
            adminLoginMessageLabel.setText("Please enter your username and/or password.");
        }
    }

<<<<<<< HEAD
    public void studentValidateLogin(ActionEvent event) throws IOException{ // test account: 1, adcbe
        //Creates connection to database
=======
    public void studentValidateLogin(ActionEvent event) throws IOException { // test account: 1, adcbe
>>>>>>> 42572a58659f29c4cc8a975adb4f4d93d996359e
        DatabaseConnection connectNow = new DatabaseConnection();
        //Gets the connection
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM student_accounts WHERE studentNumber = '" + studentUsernameField.getText() + "' AND studentPassword = '" + studentPasswordField.getText() + "';";

        try {
            //Creates a statement
            Statement statement = connectDb.createStatement();
            //Execute the query
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            //Iterate through the query result
            while(queryResult.next()) {
                //If there is a match for the username and passport in the database, loads the StudenHomePage.fxml file
                if (queryResult.getInt(1) == 1) {
                    String username = studentUsernameField.getText();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("student/StudentHomePage.fxml"));
                    //Loads the root node with the StudentHomePage.fxml file
                    root = loader.load();
                    //Gets the controller and sets the homepage
                    HomePageController homePageController = loader.getController();
                    homePageController.studentDisplayName(username);
                    homePageController.getAnnouncements();
                    //Gets the current stage
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    //Shows stage
                    stage.show();
                } else {
                    //Sends an error if credentials do not match
                    studentLoginMessageLabel.setText("Invalid login. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void adminValidateLogin(ActionEvent event) { // test account: ryan.mayfield, holag
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM admin_accounts WHERE adminID = '" + adminUsernameField.getText() + "' AND adminPassword = '" + adminPasswordField.getText() + "';";

        try {
            //Creates a statement to execute the SQL query
            Statement statement = connectDb.createStatement();
            //Execute the SQL query and get the results
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    //Gets the admin's username
                    String username = adminUsernameField.getText();
                    //Load the admin home page FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/adminHomePage.fxml"));
                    root = loader.load();
                    //Gets the controller and sets the admin homepage
                    HomePageController homePageController = loader.getController();
                    homePageController.setUsername(username);
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    //Shows stage
                    stage.show();
                } else {
                    adminLoginMessageLabel.setText("Invalid login. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}