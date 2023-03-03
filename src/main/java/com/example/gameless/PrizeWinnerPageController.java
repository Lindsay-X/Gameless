package com.example.gameless;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrizeWinnerPageController implements Initializable{

    public Label studentNumberLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private VBox prizeWinnerBoxes;
    String username;

    public void initialize(URL arg0, ResourceBundle arg1){}

    //Method to get all events, either for student or admin
    public void getWinners() throws IOException {
        //Create a connection to the database
        DatabaseConnection connectNow1 = new DatabaseConnection();
        Connection connectDb1 = connectNow1.getConnection();
        //SQL to get the student number of students with the highest points
        String getHighestPoints = "SELECT  studentNumber, studentPoints\n" +
                "FROM student_accounts\n" +
                "WHERE studentPoints = (SELECT MAX(studentPoints) FROM student_accounts);";

        String studentNumbers = "";

        try {
            //Execute the query to get the student number of students with the highest points
            Statement statement1 = connectDb1.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getHighestPoints);

            //Concatenate the student numbers into a string
            while (queryResult1.next()) {
                studentNumbers += ", " + (queryResult1.getString("studentNumber"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        //Display the student numbers on the GUI
        if (studentNumbers.length() >= 110) {
            studentNumberLabel.setText("Too many");
        } else {
            studentNumberLabel.setText(studentNumbers.substring(2));
        }

        //Establish database connection
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        //Select all prizes from prizes table
        String getPrizes = "SELECT * FROM prizes;";

        try {
            Statement statement1 = connectDb.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getPrizes);

            //Loop through each prize
            while (queryResult1.next()) {
                //Choose which FXML file to load based on user type
                String boxURL;
                boxURL = "admin/PrizeWinnerBox.fxml";
                //Load FXML file and gets controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource(boxURL));
                root = loader.load();
                PrizeWinnerBoxController prizeBoxPageController = loader.getController();
                prizeBoxPageController.initData(queryResult1.getString("prizeName"), queryResult1.getString("prizeWinner9"), queryResult1.getString("prizeWinner10"), queryResult1.getString("prizeWinner11"), queryResult1.getString("prizeWinner12"));

                //Add the prize winner box to the prizeWinnerBoxes
                prizeWinnerBoxes.getChildren().add(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //Button Actions
    public void backButtonOnAction(ActionEvent event) throws IOException {
        //Load the FXML file for the admin prize page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminPrizePage.fxml"));
        root = loader.load();
        //Load the root node of the FXML hierarchy
        PrizePageController prizePageController = loader.getController();
        prizePageController.isStudent = false;
        prizePageController.username = username;
        //Call the getPrizes method of the prize page controller to populate the prizes
        prizePageController.getPrizes();
        //Call the getPrizes method of the prize page controller to populate the prizes
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        //Show stage
        stage.show();
    }

    public void drawAllWinnersButtonOnAction(ActionEvent event) throws IOException {
        //Establish database connection and query to get all prizes and their cost
        DatabaseConnection connectNow2 = new DatabaseConnection();
        Connection connectDb2 = connectNow2.getConnection();
        String getPrizes = "SELECT prizeName, prizeCost FROM prizes";

        try {
            //Loop through each grade level
            for (int j = 9; j <= 12; j++) {
                //Execute query to get all prizes
                Statement statement1 = connectDb2.createStatement();
                ResultSet queryResult1 = statement1.executeQuery(getPrizes);

                //Loop through each prize
                while (queryResult1.next()) {
                    //Create a list of all eligible students for the prize
                    ArrayList<String> raffle = new ArrayList<String>();

                    //Establish database connection and query to get all students eligible for the prize
                    DatabaseConnection connectNow1 = new DatabaseConnection();
                    Connection connectDb1 = connectNow1.getConnection();
                    String getStudentInfo = "SELECT * FROM student_accounts WHERE studentGrade=" + j + " AND studentPoints>=" + queryResult1.getString("PrizeCost") + ";";

                    try {
                        //Execute query to get all eligible students
                        Statement statement = connectDb1.createStatement();
                        ResultSet queryResult = statement.executeQuery(getStudentInfo);

                        //Add each eligible student to the raffle list a number of times equal to their point total
                        while (queryResult.next()) {
                            for (int i = 0; i < queryResult.getInt("studentPoints") + 1; i++) {
                                raffle.add(queryResult.getString("studentNumber"));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getCause();
                    }

                    //If there are eligible students, randomly select a winner and update the prize in the database
                    if (raffle.size() != 0) {
                        String winnerID = raffle.get((int) (Math.random() * raffle.size()));

                        DatabaseConnection connectNow = new DatabaseConnection();
                        Connection connectDb = connectNow.getConnection();
                        String putWinner = "UPDATE prizes SET `prizeWinner" + j + "` = '" + winnerID + "' WHERE prizeName = '" + queryResult1.getString("prizeName") + "';";

                        try {
                            Statement statement = connectDb.createStatement();
                            statement.execute(putWinner);
                        } catch (Exception e) {
                            e.printStackTrace();
                            e.getCause();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        //Load the prize winner page to display the winners
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminPrizeWinnerPage.fxml"));
        root = loader.load();
        PrizeWinnerPageController prizeWinnerPageController = loader.getController();
        prizeWinnerPageController.getWinners();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
