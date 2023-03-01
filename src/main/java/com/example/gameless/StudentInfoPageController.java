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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentInfoPageController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ChoiceBox<String> adminStudentGradeChoice;
    private String[] grades = {"All", "Grade 9", "Grade 10", "Grade 11", "Grade 12"};

    @FXML
    private TableView<StudentInfo> studentInfoTable;
    @FXML
    private TableColumn<StudentInfo, String> studentNumber;
    @FXML
    private TableColumn<StudentInfo, String> firstName;
    @FXML
    private TableColumn<StudentInfo, String> lastName;
    @FXML
    private TableColumn<StudentInfo, Integer> grade;
    @FXML
    private TableColumn<StudentInfo, Integer> points;

    //A list of random student infos
    ObservableList<StudentInfo> list = FXCollections.observableArrayList();

    public void initialize(URL arg0, ResourceBundle arg1){
        adminStudentGradeChoice.getItems().addAll(grades);

        studentNumber.setCellValueFactory(new PropertyValueFactory<StudentInfo, String>("studentNumber"));
        firstName.setCellValueFactory(new PropertyValueFactory<StudentInfo, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<StudentInfo, String>("lastName"));
        grade.setCellValueFactory(new PropertyValueFactory<StudentInfo, Integer>("grade"));
        points.setCellValueFactory(new PropertyValueFactory<StudentInfo, Integer>("points"));
    }

    public void getStudents() {
        DatabaseConnection connectNow1 = new DatabaseConnection();
        Connection connectDb1 = connectNow1.getConnection();
        String getStudentInfo = "SELECT * FROM student_accounts;";

        try {
            Statement statement = connectDb1.createStatement();
            ResultSet queryResult = statement.executeQuery(getStudentInfo);

            while (queryResult.next()) {
                list.add(new StudentInfo(queryResult.getString("studentNumber"), queryResult.getString("studentFirstName"), queryResult.getString("studentLastName"), queryResult.getInt("studentGrade"), queryResult.getInt("studentPoints")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        studentInfoTable.setItems(list);
    }

    public void getGrade(ActionEvent event){
        String gradeChosen = adminStudentGradeChoice.getValue();
        for ( int i = 0; i<studentInfoTable.getItems().size(); i++) {
            studentInfoTable.getItems().clear();
        }
        //try {
            if (gradeChosen != "All") {

            } else {

            }
        //} catch (IOException e) {
         //   throw new RuntimeException(e);
        //}
    }

    public void backButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminHomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
