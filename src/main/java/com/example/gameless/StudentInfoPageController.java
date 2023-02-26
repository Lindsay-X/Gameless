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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentInfoPageController implements Initializable {

    @FXML
    private ChoiceBox<String> adminStudentGradeChoice;
    private String[] grade = {"All", "Grade 9", "Grade 10", "Grade 11", "Grade 12"};

    @FXML
    private TableView<StudentInfo> studentInfoTable;
    @FXML
    private TableColumn<StudentInfo, Long> studentNumber;
    @FXML
    private TableColumn<StudentInfo, String> fullName;
    @FXML
    private TableColumn<StudentInfo, Integer> points;

    //A list of random student infos
    ObservableList<StudentInfo> list = FXCollections.observableArrayList(
            new StudentInfo(335740111, "Jessica Sun", 409),
            new StudentInfo(335330133, "Raymond Zhang", 500),
            new StudentInfo(321345233, "Joe Zhang", 210),
            new StudentInfo(421329211, "Yo Mama", 6969),
            new StudentInfo(335309111, "Bob the Builder", 409),
            new StudentInfo(324360733, "Natsu Dragneel", 500),
            new StudentInfo(369817233, "I've never felt emotions like this before", 210),
            new StudentInfo(234569876, "Ver Vermillion", 60),
            new StudentInfo(345115023, "Enna Alouette", 409),
            new StudentInfo(332140122, "Millie Zhu", 500),
            new StudentInfo(321345233, "Joe Zhang", 210),
            new StudentInfo(421329211, "Yoyo Kun", 68),
            new StudentInfo(421329211, "Yo Mama", 72),
            new StudentInfo(335740111, "Jessica Sun", 1),
            new StudentInfo(335330133, "Ray of Sun", 0),
            new StudentInfo(321345233, "Joe Zhang", 210),
            new StudentInfo(421329211, "Sawako Kuronuma", 586),
            new StudentInfo(421329211, "Yo Mama", 72),
            new StudentInfo(335740111, "Jessica Sun", 1),
            new StudentInfo(335330133, "Ray of Sun", 0),
            new StudentInfo(321345233, "Joe Zhang", 210),
            new StudentInfo(421329211, "Daichi Chichi", 56)
    );

    public void initialize(URL arg0, ResourceBundle arg1){
        adminStudentGradeChoice.getItems().addAll(grade);

        studentNumber.setCellValueFactory(new PropertyValueFactory<StudentInfo, Long>("studentNumber"));
        fullName.setCellValueFactory(new PropertyValueFactory<StudentInfo, String>("fullName"));
        points.setCellValueFactory(new PropertyValueFactory<StudentInfo, Integer>("points"));
        studentInfoTable.setItems(list);
    }

    public void getGrade(ActionEvent event){
        String gradeChosen = adminStudentGradeChoice.getValue();
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void backButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminHomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
