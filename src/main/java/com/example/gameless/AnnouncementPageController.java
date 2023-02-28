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
        root = FXMLLoader.load(getClass().getResource("admin/AdminHomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addAnnouncementButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminAnnouncementAddPage.fxml"));
        root = loader.load();
        AddAnnouncementPageController addAnnouncementPageController = loader.getController();
        addAnnouncementPageController.username = username;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getAnnouncements() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String getAnnouncements = "SELECT announcementSender, announcementMsg FROM announcements;";

        try {
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getAnnouncements);

            while (queryResult1.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminAnnouncementBox.fxml"));
                root = loader.load();
                AnnouncementPageBoxController announcementPageBoxController = loader.getController();
                announcementPageBoxController.teacherLabel.setText(queryResult1.getString("announcementSender"));
                announcementPageBoxController.msgLabel.setText(queryResult1.getString("announcementMsg"));
                announcementBoxes.getChildren().add(0, root);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
