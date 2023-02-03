package com.example.gameless;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentInfoPageController implements Initializable {
    @FXML
    private Label myLabel;

    @FXML
    private ChoiceBox<String> AdminStudentGradeChoice;
    private String[] Grade = {"Grade 9", "Grade 10", "Grade 11"};

    public void initialize(URL arg0, ResourceBundle arg1){
        AdminStudentGradeChoice.getItems().addAll(Grade);
    }
}
