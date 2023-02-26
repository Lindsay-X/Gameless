package com.example.gameless;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EventBox  extends VBox {

    VBox eventBox;

    public EventBox() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("admin/EventBox.fxml"));
        eventBox = null;
        try {
            eventBox = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
