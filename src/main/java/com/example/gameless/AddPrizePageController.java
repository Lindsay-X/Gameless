package com.example.gameless;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddPrizePageController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField prizeName;
    @FXML
    private TextField prizeCost;
    @FXML
    private TextArea prizeDescription;

    @FXML
    private Button publishPrizeButton = new Button();

    @FXML
    private Label characterCount;
    String username;

    public void initialize(URL arg0, ResourceBundle arg1){
        publishPrizeButton.setDisable(true);
        characterCount.textProperty().bind(prizeDescription.textProperty()
                .length()
                .asString("Character Count: %d"));
    }

    //method to disable to the publish button if the inputs are invalid
    public void keyReleasedProperty(){
        String name = prizeName.getText();
        String desc = prizeDescription.getText();
        String point = prizeCost.getText();
        boolean isDisabled = (name.isEmpty() || name.trim().isEmpty()) ||
                (point.isEmpty() || point.trim().isEmpty() || !point.matches("0|[1-9]\\d*")) ||
                (desc.isEmpty() || desc.trim().isEmpty() || desc.length()>450);
        publishPrizeButton.setDisable(isDisabled);
    }

    //action for when back button is pressed --> goes back to prize page
    public void backPrizeButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminPrizePage.fxml"));
        root = loader.load();
        PrizePageController prizePageController = loader.getController();
        prizePageController.isStudent = false;
        prizePageController.username = username;
        prizePageController.getPrizes();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //action for when publish button is pressed
    public void publishPrizeButtonOnAction(ActionEvent event) throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String addAnnouncement = "INSERT INTO prizes (prizeName, prizeDescription, prizeCost) VALUES ('" + prizeName.getText() + "', '" + prizeDescription.getText() + "', " + prizeCost.getText() + ")";

        try {
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(addAnnouncement);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminPrizePage.fxml"));
        root = loader.load();
        PrizePageController prizePageController = loader.getController();
        prizePageController.username = username;
        prizePageController.isStudent = false;
        prizePageController.getPrizes();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
