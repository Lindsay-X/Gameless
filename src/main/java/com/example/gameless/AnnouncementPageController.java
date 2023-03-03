package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AnnouncementPageController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    VBox announcementBoxes;
    String username;

    public void backButtonOnAction(ActionEvent event) throws IOException {
        String username = this.username;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/adminHomePage.fxml"));
        root = loader.load();
        HomePageController homePageController = loader.getController();
        homePageController.setUsername(username);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addAnnouncementButtonOnAction(ActionEvent event) throws IOException {
        //Load the FXML file for the admin announcement page and set it as the root object
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminAnnouncementAddPage.fxml"));
        root = loader.load();
        //Get the controller for the admin announcement page and set username attribute to the current user's username
        AddAnnouncementPageController addAnnouncementPageController = loader.getController();
        addAnnouncementPageController.username = username;
        //Get the stage from the event source and set the new scene to the root object
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        //Show updated stage
        stage.show();
    }

    public void getAnnouncements() {
        //Create a database connection object and get a connection to the database
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        //Define SQL query to retrieve announcements
        String getAnnouncements = "SELECT announcementSender, announcementMsg, announcementID FROM announcements;";

        try {
            //Create statement object and execute the query
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getAnnouncements);

            //Sets the announcer name and message and adds to the announcement box for each announcement
            while (queryResult1.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminAnnouncementBox.fxml"));
                root = loader.load();
                AnnouncementPageBoxController announcementPageBoxController = loader.getController();
                announcementPageBoxController.teacherLabel.setText(queryResult1.getString("announcementSender"));
                announcementPageBoxController.msgLabel.setText(queryResult1.getString("announcementMsg"));
                announcementPageBoxController.username = username;
                announcementPageBoxController.announcementID = queryResult1.getInt("announcementID");
                announcementBoxes.getChildren().add(0, root);
            }
        } catch (Exception e) {
            //Print
            e.printStackTrace();
            e.getCause();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
