package com.pbl.controller;

import com.pbl.model.*;

import java.io.IOException;
import java.sql.Connection;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import javax.sound.sampled.UnsupportedAudioFileException;


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
    private Timbre timbreAtual = new TimbreAbelhaEletrica();

    public void selecionarTimbreAbelhaEletrica() {
        this.timbreAtual = new TimbreAbelhaEletrica();
    }

    public void selecionarTimbreAlienigena() {
        this.timbreAtual = new TimbreAlienigena();
    }

    public void selecionarTimbreArcoIris() {
        this.timbreAtual = new TimbreArcoIris();
    }

    public void selecionarTimbreCarrinhoDeSorvete() {
        this.timbreAtual = new TimbreCarrinhoDeSorvete();
    }

    public void selecionarTimbrePiano() {
        this.timbreAtual = new TimbrePiano();
    }

    public void selecionarTimbrePuro() {
        this.timbreAtual = new TimbrePuro();
    }

    public void selecionarTimbreTrompete() {
        this.timbreAtual = new TimbreTrompete();
    }

    public void selecionarTimbreViolao() {
        this.timbreAtual = new TimbreViolao();
    }
    String caminhoDesktop = System.getProperty("user.home") + "/Desktop";
    String caminhoAudio = caminhoDesktop + "/audio.wav";

    public void gerarAudio() {
        // Crie uma instância da classe PBL
        PBL pbl = new PBL();

        // Defina os parâmetros para o experimento
        Ambiente ambiente = new Ambiente(1, "ar", 330);
        Fonte fonte = new Fonte(4, 440, this.timbreAtual); // Use o timbre selecionado
        Experimento exp = new Experimento(1, "Experimento de teste", 25,1, -10, 1, 5, 44100, ambiente, fonte);

        // ...


        // Tente criar o arquivo
        try {
            pbl.criarArquivo(caminhoAudio, exp);
            System.out.println("Arquivo de áudio criado com sucesso em: " + caminhoAudio);
        } catch (IOException | UnsupportedAudioFileException e) {
            System.out.println("Erro ao criar arquivo de áudio: " + e.getMessage());
        }
    }
    @FXML
    private void handleCloseButtonAction() {
        Stage stage = (Stage) topBar.getScene().getWindow();
        stage.close();
    }
}
