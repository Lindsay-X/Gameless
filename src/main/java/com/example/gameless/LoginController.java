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
        root = FXMLLoader.load(getClass().getResource("admin/AdminLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void adminStudentButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("student/StudentLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void studentLoginButtonOnAction(ActionEvent event) throws IOException{
        if (!studentUsernameField.getText().isBlank() && !studentPasswordField.getText().isBlank()) {
            studentValidateLogin(event);
        } else {
            studentLoginMessageLabel.setText("Please enter your student number and/or password.");
        }
    }

    public void adminLoginButtonOnAction(ActionEvent event) {
        if (!adminUsernameField.getText().isBlank() && !adminPasswordField.getText().isBlank()) {
            adminValidateLogin(event);
        } else {
            adminLoginMessageLabel.setText("Please enter your username and/or password.");
        }
    }

    public void studentValidateLogin(ActionEvent event) throws IOException { // test account: 1, adcbe
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM student_accounts WHERE studentNumber = '" + studentUsernameField.getText() + "' AND studentPassword = '" + studentPasswordField.getText() + "';";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    String username = studentUsernameField.getText();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("student/StudentHomePage.fxml"));
                    root = loader.load();
                    HomePageController homePageController = loader.getController();
                    homePageController.studentDisplayName(username);
                    homePageController.getAnnouncements();
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
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
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    String username = adminUsernameField.getText();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/adminHomePage.fxml"));
                    root = loader.load();
                    HomePageController homePageController = loader.getController();
                    homePageController.setUsername(username);
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
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