package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrizeWinnerPageController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private VBox prizeWinnerBoxes;

    public void initialize(URL arg0, ResourceBundle arg1){}

    //Method to get all events, either for student or admin
    public void getWinners() throws IOException {
        //Establish database connection
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        //Select all prizes from prizes table
        String getPrizes = "SELECT * FROM prizes;";

        try {
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getPrizes);

            //Loop through each prize
            while (queryResult1.next()) {
                //Choose which FXML file to load based on user type
                String boxURL;
                boxURL = "admin/PrizeWinnerBox.fxml";
                //Load FXML file and gets controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource(boxURL));
                root = loader.load();
                PrizeWinnerBoxController prizeBoxPageController = loader.getController();
                prizeBoxPageController.initData(queryResult1.getString("prizeName"), queryResult1.getString("prizeWinner9"), queryResult1.getString("prizeWinner10"), queryResult1.getString("prizeWinner11"), queryResult1.getString("prizeWinner12"));

                //Add the prize winner box to the prizeWinnerBoxes
                prizeWinnerBoxes.getChildren().add(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //Button Actions
    public void backButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminPrizePage.fxml"));
        root = loader.load();
        PrizePageController prizePageController = loader.getController();
        prizePageController.isStudent = false;
        prizePageController.getPrizes();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void drawAllWinnersButtonOnAction(ActionEvent event) {
    }
}
