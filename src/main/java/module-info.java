module com.example.gameless {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.example.gameless to javafx.fxml;
    exports com.example.gameless;
}