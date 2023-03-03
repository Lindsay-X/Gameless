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

    String username;
    @FXML
    public TextArea announcementMsg;
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

    //method to disable to the publish button if the inputs are invalid
    public void keyReleasedProperty(){
        String msg = announcementMsg.getText();
        boolean isDisabled = (msg.isEmpty() || msg.trim().isEmpty() || msg.length()>200);
        publishAnnouncementButton.setDisable(isDisabled);
    }

    //action for when back button is pressed --> goes back to announcement page
    public void backAnnouncementButtonOnAction(ActionEvent event) throws IOException {
        //Load the FXML file for the admin event page and set it as the root object
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminAnnouncementPage.fxml"));
        root = loader.load();
        //Load the events onto the event page
        AnnouncementPageController announcementPageController = loader.getController();
        announcementPageController.username = username;
        announcementPageController.getAnnouncements();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //Get the stage from the event source and set the new scene to the root object
        scene = new Scene(root);
        stage.setScene(scene);
        //Show the updated stage with the new scene
        stage.show();
    }

    //action for when publish button is pressed
    public void publishButtonOnAction(ActionEvent event) throws IOException {
        //Connect to database
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        //Define SQL statement to add new event to database
        DatabaseConnection connectNow1 = new DatabaseConnection();
        Connection connectDb1 = connectNow1.getConnection();
        String name = "";

        String getName = "SELECT adminFirstName, adminLastName FROM admin_accounts WHERE adminID = '" + username + "';";

        try {
            //Execute SQL statement
            Statement statement = connectDb1.createStatement();
            ResultSet queryResult = statement.executeQuery(getName);

            while(queryResult.next()) {
                name += queryResult.getString("adminFirstName") + " " + queryResult.getString("adminLastName");
            }
        } catch (Exception e) {
            //Print errors that occur
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
            //Execute SQL statement
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(addAnnouncement);
        } catch (Exception e) {
            //Print errors that occur
            e.printStackTrace();
            e.getCause();
        }

        //Load admin event page and set it as the root object
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminAnnouncementPage.fxml"));
        root = loader.load();
        //Get the class from the FXML loader and calls a method to update events
        AnnouncementPageController announcementPageController = loader.getController();
        announcementPageController.username = username;
        announcementPageController.getAnnouncements();
        //Set the new scene to the root object and display the updated stage
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        //Show stage
        stage.show();
    }
}
