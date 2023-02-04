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

    public void studentLoginButtonOnAction(ActionEvent event) {
        if (!studentUsernameField.getText().isBlank() && !studentPasswordField.getText().isBlank()) {
            studentValidateLogin();
        } else {
            studentLoginMessageLabel.setText("Please enter your student number and/or password.");
        }
    }

    public void studentValidateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM student_accounts WHERE studentNumber = '" + studentUsernameField.getText() + "' AND studentPassword = '" + studentPasswordField.getText() + "';";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    studentLoginMessageLabel.setText("yay");
                } else {
                    studentLoginMessageLabel.setText("bruh");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }
}