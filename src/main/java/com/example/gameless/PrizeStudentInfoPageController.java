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
import javafx.scene.chart.PieChart;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrizeStudentInfoPageController implements Initializable {
    public int pointThreshold;
    @FXML
    Label winnerLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    int selectedGrade;
    String prizeName;
    @FXML
    private ChoiceBox<String> adminStudentGradeChoice;
    private String[] grades = {"Grade 9", "Grade 10", "Grade 11", "Grade 12"};
    private String prizeWinnerID;
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
    ObservableList<StudentInfo> list = FXCollections.observableArrayList();

    public void initialize(URL arg0, ResourceBundle arg1){
        adminStudentGradeChoice.getItems().addAll(grades);
        adminStudentGradeChoice.setOnAction(this::getGrade);

        studentNumber.setCellValueFactory(new PropertyValueFactory<StudentInfo, String>("studentNumber"));
        firstName.setCellValueFactory(new PropertyValueFactory<StudentInfo, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<StudentInfo, String>("lastName"));
        grade.setCellValueFactory(new PropertyValueFactory<StudentInfo, Integer>("grade"));
        points.setCellValueFactory(new PropertyValueFactory<StudentInfo, Integer>("points"));
    }

    public void getStudents(int grade) {
        DatabaseConnection connectNow2 = new DatabaseConnection();
        Connection connectDb2 = connectNow2.getConnection();
        String getWinner = "SELECT `prizeWinner" + grade + "` FROM prizes WHERE prizeName='" + prizeName + "';";

        DatabaseConnection connectNow3 = new DatabaseConnection();
        Connection connectDb3 = connectNow3.getConnection();
        try {
            Statement statement = connectDb3.createStatement();
            ResultSet queryResult = statement.executeQuery(getWinner);

            queryResult.next();
            prizeWinnerID = queryResult.getString("prizeWinner" + grade);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        String winnerName = "None";

        if (!prizeWinnerID.equals("None")) {
            String getWinnerName = "SELECT studentFirstName, studentLastName FROM student_accounts WHERE studentNumber = '" + prizeWinnerID + "';";
            try {
                Statement statement = connectDb2.createStatement();
                ResultSet queryResult = statement.executeQuery(getWinnerName);

                queryResult.next();
                winnerName = (queryResult.getString("studentFirstName") + " " + queryResult.getString("studentLastName") + " (" + prizeWinnerID + ")");
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }

        winnerLabel.setText(winnerName);

        DatabaseConnection connectNow1 = new DatabaseConnection();
        Connection connectDb1 = connectNow1.getConnection();
        String getStudentInfo = "SELECT * FROM student_accounts WHERE studentGrade=" + grade + " AND studentPoints>=" + pointThreshold + ";";

        try {
            Statement statement = connectDb1.createStatement();
            ResultSet queryResult = statement.executeQuery(getStudentInfo);

            list.clear();

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
        for ( int i = 0; i < studentInfoTable.getItems().size(); i++) {
            studentInfoTable.getItems().clear();
        }
        selectedGrade = Integer.parseInt(gradeChosen.substring(6));
        getStudents(selectedGrade);
    }


    public void backPrizeButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminPrizePage.fxml"));
        root = loader.load();
        PrizePageController prizePageController = loader.getController();
        prizePageController.getPrizes();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void drawStudentButtonOnAction(ActionEvent event) throws IOException {
        StudentInfo winnerID = list.get((int)(Math.random() * list.size()));

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String putWinner = "UPDATE prizes SET `prizeWinner" + selectedGrade + "` = '" + winnerID.getStudentNumber() + "' WHERE prizeName = '" + prizeName + "';";

        try {
            Statement statement = connectDb.createStatement();
            statement.execute(putWinner);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        getStudents(selectedGrade);
    }
}
