package com.example.gameless;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    private Button AdminStudentButton;

    public void AdminStudentButtonOnAction(ActionEvent event){
        Stage stage = (Stage) AdminStudentButton.getScene().getWindow();
        stage.close();
    }
}