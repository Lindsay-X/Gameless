package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PrizePageController {
    @FXML
    public Label coinValue;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    GridPane prizeBoxes;
    String username;
    boolean isStudent;

    public void backButtonOnAction(ActionEvent event) throws IOException {
        //Method retrieves username, sets it to be displayed on home page, and redirects to the adminHomePage.fxml file when the back button is clicked.
        String username = this.username;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/adminHomePage.fxml"));
        root = loader.load();
        HomePageController homePageController = loader.getController();
        //Sets username to be displayed on home page
        homePageController.setUsername(username);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        //Show stage
        stage.show();
    }

    public void studentBackButtonOnAction(ActionEvent event) throws IOException {
        //Method redirects to the studentHomePage.fxml file when the back button is clicked
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student/StudentHomePage.fxml"));
        root = loader.load();
        HomePageController homePageController = loader.getController();
        //Sets the username to be displayed on the HomePage
        homePageController.studentDisplayName(username);
        //Retrieves the announcements to be displayed on the HomePage
        homePageController.getAnnouncements();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        //Show stage
        stage.show();
    }

    public void addPrizeButtonOnAction(ActionEvent event) throws IOException {
        //Load the AdminPrizeAddPage.fxml file and retrieve its controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminPrizeAddPage.fxml"));
        root = loader.load();
        AddPrizePageController addPrizePageController = loader.getController();
        //Set the 'username' variable in the AddPrizePageController to the current user's username
        addPrizePageController.username = username;
        //Set the scene to the new root and display the stage
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getPrizes() throws IOException {
        //Create a new DatabaseConnection instance and establish a connection to the database
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        //Define a SQL query to retrieve all prizes from the database
        String getPrizes = "SELECT prizeName, prizeDescription, prizeCost FROM prizes;";

        try {
            //Create a new Statement instance and execute the SQL query
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getPrizes);

            //Initialize a counter for positioning prize boxes in the grid pane
            int counter = 0;
            while (queryResult1.next()) {
                FXMLLoader loader;
                if (!isStudent) {
                    //Load the PrizeBox.fxml file if the user is an admin
                    loader = new FXMLLoader(getClass().getResource("admin/PrizeBox.fxml"));
                } else {
                    //Load either the StudentPrizeBox.fxml or StudentDisabledPrizeBox.fxml file
                    if (queryResult1.getInt("prizeCost") <= Integer.parseInt(coinValue.getText())) {
                        loader = new FXMLLoader(getClass().getResource("student/StudentPrizeBox.fxml"));
                    } else {
                        loader = new FXMLLoader(getClass().getResource("student/StudentDisabledPrizeBox.fxml"));
                    }
                }
                //Load the prize box FXML file and initialize the PrizeBoxController with the prize information
                root = loader.load();
                PrizeBoxController prizeBoxController = loader.getController();
                prizeBoxController.initData(queryResult1.getString("prizeName"), queryResult1.getString("prizeDescription"), queryResult1.getInt("prizeCost"));
                prizeBoxController.username = username;

                //Add the prize box to the grid pane at the appropriate position
                prizeBoxes.add(root, counter % 3, counter / 3, 1, 1);
                counter++;
            }
        } catch (Exception e) {
            //Print the stack trace and the cause of the exception if an error occurs
            e.printStackTrace();
            e.getCause();
        }
    }

    public void viewWinnersButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminPrizeWinnerPage.fxml"));
        root = loader.load();
        PrizeWinnerPageController prizeWinnerPageController = loader.getController();
        prizeWinnerPageController.username = username;
        prizeWinnerPageController.getWinners();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
