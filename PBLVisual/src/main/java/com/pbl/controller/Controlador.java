package com.pbl.controller;

import com.pbl.model.ConexaoBD;
import java.sql.Connection;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class Controlador {
    //    ConexaoBD conx = new ConexaoBD();
    //    Connection connection = conx.getConexao();

    @FXML
    private HBox topBar;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {
        topBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        topBar.setOnMouseDragged(event -> {
            Stage stage = (Stage) topBar.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    private void handleCloseButtonAction() {
        Stage stage = (Stage) topBar.getScene().getWindow();
        stage.close();
    }
}
