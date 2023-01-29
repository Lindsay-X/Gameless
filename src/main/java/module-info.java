module com.example.gameless {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gameless to javafx.fxml;
    exports com.example.gameless;
}