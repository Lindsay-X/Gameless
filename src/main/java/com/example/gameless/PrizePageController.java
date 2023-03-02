package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PrizePageController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    GridPane prizeBoxes;

    public void backButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminHomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void studentBackButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("student/StudentHomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addPrizeButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminPrizeAddPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void enterDrawButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminPrizeAddPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getPrizes() throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String getPrizes = "SELECT prizeName, prizeDescription, prizeCost FROM prizes;";

        try {
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getPrizes);

            int counter = 0;
            while (queryResult1.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/PrizeBox.fxml"));
                root = loader.load();
                PrizeBoxController prizeBoxController = loader.getController();
                prizeBoxController.initData(queryResult1.getString("prizeName"), queryResult1.getString("prizeDescription"), queryResult1.getInt("prizeCost"));

                prizeBoxes.add(root, counter % 3, counter / 3, 1, 1);
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
