package com.pbl.controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Aplicacao extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = getClass().getResource("/com/pbl/view/inicio.fxml");
        if (fxmlLocation == null) {
            throw new IOException("FXML file not found: /com/pbl/view/inicio.fxml");
        }
        System.out.println("FXML file found at: " + fxmlLocation);

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 600, 800);
        stage.setTitle("Efeito Doppler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
