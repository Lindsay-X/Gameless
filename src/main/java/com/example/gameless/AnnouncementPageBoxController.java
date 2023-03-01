package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class AnnouncementPageBoxController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    int announcementID;
    String username;
    @FXML
    Label teacherLabel;
    @FXML
    Label msgLabel;

    public void editAnnouncementButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminAnnouncementEditPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteButtonOnAction(ActionEvent event) throws IOException {
        //Connect to database
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        //Define SQL statement to delete event from database
        String delAnnouncement = "DELETE FROM announcements WHERE announcementID='" + announcementID + "'";
        try {
            //Execute SQL statement
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(delAnnouncement);
        } catch (Exception e) {
            //Print errors that occur
            e.printStackTrace();
            e.getCause();
        }
        //Load admin event page and set it as the root object
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminAnnouncementPage.fxml"));
        root = loader.load();
        AnnouncementPageController announcementPageController = loader.getController();
        announcementPageController.getAnnouncements();
        announcementPageController.username = username;
        //Set the new scene to the root object and display the updated stage
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
