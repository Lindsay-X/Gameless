package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomePageController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button studentInfoButton;
    @FXML
    private Button adminInfoButton;

    public void studentDisplayName(String username) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String getName = "SELECT studentFirstName, studentLastName FROM student_accounts WHERE studentNumber = '" + username + "';";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(getName);
            String name = "";

            while(queryResult.next()) {
                name += queryResult.getString("studentFirstName") + " " + queryResult.getString("studentLastName");
            }

            studentInfoButton.setText(name);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void adminDisplayName(String username) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String getName = "SELECT adminFirstName, adminLastName FROM admin_accounts WHERE adminID = '" + username + "';";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(getName);
            String name = "";

            while(queryResult.next()) {
                name += queryResult.getString("adminFirstName") + " " + queryResult.getString("adminLastName");
            }

            adminInfoButton.setText(name);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void studentInfoButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminStudentInfoPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void adminEventButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminEventPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
