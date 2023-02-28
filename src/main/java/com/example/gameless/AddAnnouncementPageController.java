package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddAnnouncementPageController {

    @FXML
    public TextArea announcementMsg;
    private Stage stage;
    private Scene scene;
    private Parent root;
    String username;

    public void backAnnouncementButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminAnnouncementPage.fxml"));
        root = loader.load();
        AnnouncementPageController announcementPageController = loader.getController();
        announcementPageController.getAnnouncements();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void publishButtonOnAction(ActionEvent event) throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        DatabaseConnection connectNow1 = new DatabaseConnection();
        Connection connectDb1 = connectNow1.getConnection();
        String name = "";

        String getName = "SELECT adminFirstName, adminLastName FROM admin_accounts WHERE adminID = '" + username + "';";

        try {
            Statement statement = connectDb1.createStatement();
            ResultSet queryResult = statement.executeQuery(getName);

            while(queryResult.next()) {
                name += queryResult.getString("adminFirstName") + " " + queryResult.getString("adminLastName");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        String addAnnouncement = "INSERT INTO announcements (announcementSender, announcementMsg) VALUES ('" + name + "', '" + announcementMsg.getText() + "')";

        try {
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(addAnnouncement);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminAnnouncementPage.fxml"));
        root = loader.load();
        AnnouncementPageController announcementPageController = loader.getController();
        announcementPageController.getAnnouncements();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
