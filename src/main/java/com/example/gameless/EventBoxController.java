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
    int id;
    String studentID;
    @FXML
    Label eventDescriptionLabel;
    @FXML
    Label eventNameLabel;
    @FXML
    Label eventTimeLabel;
    @FXML
    Label eventLocationLabel;
    @FXML
    Label eventPointsLabel;

    public void joinEventButtonOnAction(ActionEvent event) throws IOException {

    }

    public void viewEventButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminEventViewPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    void initData(int id) {
        this.id = id;
    }
}
