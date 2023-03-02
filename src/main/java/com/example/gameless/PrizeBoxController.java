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

public class PrizeBoxController {
    @FXML
    public Label prizeName;
    @FXML
    public Label prizeDescription;
    @FXML
    public Label prizeCost;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void studentListButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminPrizeStudentListPage.fxml"));
        root = loader.load();
        PrizeStudentInfoPageController prizeStudentInfoPageController = loader.getController();
        prizeStudentInfoPageController.selectedGrade = 9;
        prizeStudentInfoPageController.prizeName = prizeName.getText();
        prizeStudentInfoPageController.getStudents(prizeStudentInfoPageController.selectedGrade);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initData(String prizeName, String prizeDescription, int cost) {
        this.prizeName.setText(prizeName);
        this.prizeDescription.setText(prizeDescription);
        prizeCost.setText("Cost: " + cost);
    }
}
