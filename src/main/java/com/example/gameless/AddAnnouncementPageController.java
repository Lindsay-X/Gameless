package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddAnnouncementPageController implements Initializable {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public TextArea announcementMsg;
    String username;
    @FXML
    private Button publishAnnouncementButton;
    @FXML
    private Label characterCount;
    int announcementID;

    public void initialize(URL arg0, ResourceBundle arg1){
        publishAnnouncementButton.setDisable(true);
        characterCount.textProperty().bind(announcementMsg.textProperty()
                .length()
                .asString("Character Count: %d"));
    }

    public void keyReleasedProperty(){
        String msg = announcementMsg.getText();
        boolean isDisabled = (msg.isEmpty() || msg.trim().isEmpty() || msg.length()>200);
        publishAnnouncementButton.setDisable(isDisabled);
    }
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

        String addAnnouncement = "";
        if (publishAnnouncementButton.getText().equals("Publish")) {
            addAnnouncement = "INSERT INTO announcements (announcementSender, announcementMsg) VALUES ('" + name + "', '" + announcementMsg.getText() + "')";
        } else {
            addAnnouncement = "UPDATE announcements SET announcementMsg='" + announcementMsg.getText() + "' WHERE announcementID = " + announcementID + ";";
        }

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
