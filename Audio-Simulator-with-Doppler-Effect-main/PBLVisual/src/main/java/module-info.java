module com.pbl.model {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.pbl.model to javafx.fxml;
    exports com.pbl.model;
    exports com.pbl.controller;
    opens com.pbl.controller to javafx.fxml;
}