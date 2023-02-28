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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddPrizePageController implements Initializable {
    @FXML
    //private String[] icon = {"../../../../resources/com/example/gameless/image/balloon.png", "../../../../resources/com/example/gameless/image/coin.png", "../../../../resources/com/example/gameless/image/giftCard.png", "../../../../resources/com/example/gameless/image/hoodie.png", "../../../../resources/com/example/gameless/image/prizeBox.png", "../../../../resources/com/example/gameless/image/ticket.png"};
    private String[] icon = {""};
    private ObservableList<String> opt = FXCollections.observableArrayList();
    private ComboBox<String> prizeIconChoice;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField prizeName;
    @FXML
    private TextField prizeCost;
    @FXML
    private TextArea prizeDescription;

    @FXML
    private Button publishEventButton;

    @FXML
    private Label characterCount;

    public void initialize(URL arg0, ResourceBundle arg1){
        opt.addAll(icon);
        prizeIconChoice = new ComboBox<>(opt);
        prizeIconChoice.setButtonCell(new StatusListCell());
        prizeIconChoice.setCellFactory(e -> new StatusListCell());
        prizeIconChoice.setOnAction(this::getTag); //calls the method

        publishEventButton.setDisable(true);
        characterCount.textProperty().bind(prizeDescription.textProperty()
                .length()
                .asString("Character Count: %d"));
    }

    public class StatusListCell extends ListCell<String> {

        protected void updateItem(String item, boolean empty){
            super.updateItem(item, empty);
            setGraphic(null);
            if(item!=null){
                ImageView imageView = new ImageView(new Image(item));
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);
                setGraphic(imageView);
            }
        }

    }

    //method to get the tag value
    public void getTag(ActionEvent event) {
        String iconChosen = prizeIconChoice.getValue();
    }

    //method to disable to the publish button if the inputs are invalid
    public void keyReleasedProperty(){
        String name = prizeName.getText();
        String desc = prizeDescription.getText();
        String point = prizeCost.getText();
        boolean isDisabled = (name.isEmpty() || name.trim().isEmpty()) ||
                (point.isEmpty() || point.trim().isEmpty() || !point.matches("0|[1-9]\\d*")) ||
                (desc.isEmpty() || desc.trim().isEmpty() || desc.length()>450);
        publishEventButton.setDisable(isDisabled);
    }

    public void backPrizeButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin/AdminPrizePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

   /* public void publishButtonOnAction(ActionEvent event) throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String addEvent = "INSERT INTO events (eventName, eventDescription, eventLocation, eventTime, eventTag, eventPoints) VALUES ('" + eventName.getText() + "', '" + eventDescription.getText() + "', '" + eventLocation.getText() + "', '" + eventDate.getValue().toString() + " " + eventTime.getText() + ":00', '" + prizeIconChoice.getValue() + "', '" + eventPoints.getText() + "')";
        String addDB = "CREATE TABLE `fbla`.`" + eventName.getText() + "_participants` (\n" +
                "  `participantID` VARCHAR(45) NOT NULL,\n" +
                "  `showedUp` TINYINT(3) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY (`participantID`));";
        try {
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(addEvent);
            statement.execute(addDB);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminEventPage.fxml"));
        root = loader.load();
        EventPageController eventPageController = loader.getController();
        eventPageController.getEvents(false);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }*/
}
