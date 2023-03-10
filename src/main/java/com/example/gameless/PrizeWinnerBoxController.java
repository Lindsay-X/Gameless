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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PrizeWinnerBoxController implements Initializable {
    public Label winner9Label;
    public Label winner10Label;
    public Label winner11Label;
    public Label winner12Label;
    public Label prizeNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    //sey the winner box information
    void initData(String prizeName, String winner9, String winner10, String winner11, String winner12) {
        prizeNameLabel.setText(prizeName);
        winner9Label.setText("Grade 9: " + winner9);
        winner10Label.setText("Grade 10: " + winner10);
        winner11Label.setText("Grade 11: " + winner11);
        winner12Label.setText("Grade 12: " + winner12);
    }
}
