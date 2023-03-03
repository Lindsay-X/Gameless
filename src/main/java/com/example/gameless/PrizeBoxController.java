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
    String username;

    public void studentListButtonOnAction(ActionEvent event) throws IOException {
<<<<<<< HEAD
        //Load the AdminPrizeStudentListPage.fxml file using an FXMLLoader object
        root = FXMLLoader.load(getClass().getResource("admin/AdminPrizeStudentListPage.fxml"));
        //Get the Stage object and set it as the stage for the newly loaded scene
=======
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminPrizeStudentListPage.fxml"));
        root = loader.load();
        PrizeStudentInfoPageController prizeStudentInfoPageController = loader.getController();
        prizeStudentInfoPageController.selectedGrade = 9;
        prizeStudentInfoPageController.username = username;
        prizeStudentInfoPageController.prizeName = prizeName.getText();
        prizeStudentInfoPageController.pointThreshold = Integer.parseInt(prizeCost.getText().substring(17));
        prizeStudentInfoPageController.getStudents(prizeStudentInfoPageController.selectedGrade);
>>>>>>> 42572a58659f29c4cc8a975adb4f4d93d996359e
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //Creates a new scene object
        scene = new Scene(root);
        stage.setScene(scene);
        //Shows stage
        stage.show();
    }

    public void initData(String prizeName, String prizeDescription, int cost) {
        this.prizeName.setText(prizeName);
        this.prizeDescription.setText(prizeDescription);
        prizeCost.setText("Point Threshold: " + cost);
    }
}
