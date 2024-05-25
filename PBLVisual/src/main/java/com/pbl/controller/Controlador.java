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
    private Button buttonClose;

    @FXML
    private void handleCloseButtonAction() {
        // Obtém a janela atual através do botão
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        // Fecha a janela
        stage.close();
    }

}
