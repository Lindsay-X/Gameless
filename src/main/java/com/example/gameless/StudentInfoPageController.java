package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentInfoPageController implements Initializable {

    @FXML
    private ChoiceBox<String> adminStudentGradeChoice, adminStudentSortBy;
    private String[] grade = {"All", "Grade 9", "Grade 10", "Grade 11", "Grade 12"};
    private String[] sortBy = {"Name", "Points"};

    public void initialize(URL arg0, ResourceBundle arg1){
        adminStudentGradeChoice.getItems().addAll(grade);
        adminStudentSortBy.getItems().addAll(sortBy);
    }

    public void getGrade(ActionEvent event){
        String gradeChosen = adminStudentGradeChoice.getValue();
    }
}
