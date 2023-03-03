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

    public Label coinValue;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String username;
    @FXML
    private Button studentProfileButton;
    @FXML
    private VBox studentHomePageAnnouncementPanes;
    private int points;

    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public void studentInfoButtonOnAction(ActionEvent event) throws IOException {
<<<<<<< HEAD
        //Load the AdminStudentIntoPage.fxml file
        root = FXMLLoader.load(getClass().getResource("admin/AdminStudentInfoPage.fxml"));
        //Get the current stage and set the scene to the loaded root
=======
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminStudentInfoPage.fxml"));
        root = loader.load();
        StudentInfoPageController studentInfoPageController = loader.getController();
        studentInfoPageController.username = username;
        studentInfoPageController.getStudents();
>>>>>>> 42572a58659f29c4cc8a975adb4f4d93d996359e
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void adminEventButtonOnAction(ActionEvent event) throws IOException {
        //Load the AdminEventPage.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminEventPage.fxml"));
        root = loader.load();
        //Get the controller for the loaded page
        EventPageController eventPageController = loader.getController();
<<<<<<< HEAD
        //Call the getEvents method on the controller with parameter false
=======
        eventPageController.adminUsername = username;
>>>>>>> 42572a58659f29c4cc8a975adb4f4d93d996359e
        eventPageController.getEvents(false);
        //Get the current stage and set the scene to the loaded root
        eventPageController.setStudent(false);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void adminAnnouncementsButtonOnAction(ActionEvent event) throws IOException {
        //Load the AdminAnnouncementPage.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminAnnouncementPage.fxml"));
        root = loader.load();
        //Get the controller for the loaded page
        AnnouncementPageController announcementPageController = loader.getController();
        //Call the getAnnouncements method on the controller
        announcementPageController.getAnnouncements();
        //Set the username field of the controller to the current user's username
        announcementPageController.username = username;
        //Get the current stage and set the scene to the loaded root
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void adminPrizeButtonOnAction(ActionEvent event) throws IOException {
<<<<<<< HEAD
        //Load the AdminPrizePage.fxml file
        root = FXMLLoader.load(getClass().getResource("admin/AdminPrizePage.fxml"));
        //Get the current stage and set the scene to the loaded root
=======
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminPrizePage.fxml"));
        root = loader.load();
        PrizePageController prizePageController = loader.getController();
        prizePageController.username = username;
        prizePageController.isStudent = false;
        prizePageController.getPrizes();
>>>>>>> 42572a58659f29c4cc8a975adb4f4d93d996359e
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getAnnouncements() throws IOException {
        //Create a connection to the database
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        //Create the SQL statement to get the announcements
        String getAnnouncements = "SELECT announcementSender, announcementMsg FROM announcements;";

        try {
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getAnnouncements);

            //Loop through the query result and create a new pane for each announcement
            while (queryResult1.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("student/HomePageAnnouncementPane.fxml"));
                root = loader.load();
                HomePageAnnouncementPaneController homePageAnnouncementPaneController = loader.getController();
                //Initialize the data of the pane with the announcement information
                homePageAnnouncementPaneController.initData(queryResult1.getString("announcementSender"), queryResult1.getString("announcementMsg"));

                //Add the pane to the studentHomePageAnnouncementPanes VBox
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

        String getName = "SELECT studentFirstName, studentLastName, studentPoints FROM student_accounts WHERE studentNumber = '" + username + "';";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(getName);
            String name = "";

            queryResult.next();
            name += queryResult.getString("studentFirstName") + " " + queryResult.getString("studentLastName");

            studentProfileButton.setText(name);
            points = queryResult.getInt("studentPoints");
            coinValue.setText("" + points);

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
                profilePageController.displayStudentInfo(username, queryResult.getString("studentFirstName") + " " + queryResult.getString("studentLastName"), queryResult.getInt("studentPoints"), queryResult.getInt("studentGrade"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void studentEventButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student/studentEventPage.fxml"));
        root = loader.load();
        EventPageController eventPageController = loader.getController();
        eventPageController.setStudentID(username);
        eventPageController.getEvents(true);
        eventPageController.setStudent(true);
        eventPageController.coinValue.setText("" + points);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void studentPrizeButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student/StudentPrizePage.fxml"));
        root = loader.load();
        PrizePageController prizePageController = loader.getController();
        prizePageController.username = username;
        prizePageController.coinValue.setText("" + points);
        prizePageController.isStudent = true;
        prizePageController.getPrizes();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
