package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String username;
    @FXML
    private Button studentProfileButton;
    @FXML
    private VBox studentHomePageAnnouncementPanes;

    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public void getAnnouncements() throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String getAnnouncements = "SELECT announcementSender, announcementMsg FROM announcements;";

        try {
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getAnnouncements);

            while (queryResult1.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("student/HomePageAnnouncementPane.fxml"));
                root = loader.load();
                HomePageAnnouncementPaneController homePageAnnouncementPaneController = loader.getController();
                homePageAnnouncementPaneController.initData(queryResult1.getString("announcementSender"), queryResult1.getString("announcementMsg"));

                studentHomePageAnnouncementPanes.getChildren().add(0, root);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void studentDisplayName(String username) {
        this.username = username;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String getName = "SELECT studentFirstName, studentLastName FROM student_accounts WHERE studentNumber = '" + username + "';";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(getName);
            String name = "";

            while(queryResult.next()) {
                name += queryResult.getString("studentFirstName") + " " + queryResult.getString("studentLastName");
            }

            studentProfileButton.setText(name);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void studentProfileButtonOnAction(ActionEvent event) throws IOException {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = "SELECT * FROM student_accounts WHERE studentNumber = '" + username + "';";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("student/StudentProfilePage.fxml"));
                root = loader.load();
                ProfilePageController profilePageController = loader.getController();
                profilePageController.displayStudentInfo(username, queryResult.getString("studentFirstName") + " " + queryResult.getString("studentFirstName"), queryResult.getInt("studentPoints"), queryResult.getInt("studentGrade"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        root = FXMLLoader.load(getClass().getResource("student/StudentProfilePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void studentInfoButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminStudentInfoPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void adminEventButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminEventPage.fxml"));
        root = loader.load();
        EventPageController eventPageController = loader.getController();
        eventPageController.getEvents(false);
        eventPageController.setStudent(false);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void adminAnnouncementsButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminAnnouncementPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void adminPrizeButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminPrizePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void studentEventButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student/studentEventPage.fxml"));
        root = loader.load();
        EventPageController eventPageController = loader.getController();
        eventPageController.getEvents(true);
        eventPageController.setStudent(true);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
