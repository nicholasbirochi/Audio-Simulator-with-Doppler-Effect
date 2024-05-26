package com.pbl.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;

public class Aplicacao extends Application {
    @Override
    public void start(Stage stage) {
        try {
            URL fxmlLocation = getClass().getResource("/com/pbl/view/inicio.fxml");
            if (fxmlLocation == null) {
                throw new IOException("FXML file not found: /com/pbl/view/inicio.fxml");
            }
            System.out.println("FXML file found at: " + fxmlLocation);

            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

            // Define o estilo da janela como undecorated
            stage.initStyle(StageStyle.UNDECORATED);

            stage.setTitle("Efeito Doppler");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @FXML
//    public void handleVelocidadeFonteSlider() {
//        double valor = velocidadeFonte.getValue();
//        // Faça algo com o valor do slider, por exemplo:
//        System.out.println("Valor do slider: " + valor);
//        // Ou chame métodos para atualizar a interface com o novo valor, etc.
//    }

    public static void main(String[] args) {
        launch();
    }
}
