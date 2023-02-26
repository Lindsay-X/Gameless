package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EventBoxController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label eventDescriptionLabel;
    @FXML
    public Label eventNameLabel;
    @FXML
    private Label eventTimeLabel;
    @FXML
    private Label eventLocationLabel;
    @FXML
    private Label eventPointsLabel;

    public void viewEventButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminEventViewPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventNameLabel.setText("pogger");
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String getEvents = "SELECT * FROM events WHERE eventName = '" + eventNameLabel.getText() + "';";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(getEvents);

            while(queryResult.next()) {
                eventDescriptionLabel.setText(queryResult.getString("eventDescription"));
                eventNameLabel.setText(queryResult.getString("eventName"));
                eventTimeLabel.setText(queryResult.getString("eventTime"));
                eventPointsLabel.setText(queryResult.getString("eventPoints"));
                eventLocationLabel.setText(queryResult.getString("eventLocation"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
