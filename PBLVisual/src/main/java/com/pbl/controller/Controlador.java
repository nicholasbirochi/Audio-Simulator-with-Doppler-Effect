package com.pbl.controller;

import com.pbl.model.*;

import java.io.IOException;
import java.sql.Connection;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

    @FXML
    private GridPane pageTimbres;
    @FXML
    private VBox pageFonte;

    public void showPageFonte() {
        pageTimbres.setVisible(false);
        pageFonte.setVisible(true);
    }
    public void showPageTimbres() {
        pageTimbres.setVisible(true);
        pageFonte.setVisible(false);
    }

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
        System.out.println("Timbre selecionado: Abelha Elétrica");
    }

    public void selecionarTimbreAlienigena() {
        this.timbreAtual = new TimbreAlienigena();
        System.out.println("Timbre selecionado: Alienígena");
    }

    public void selecionarTimbreArcoIris() {
        this.timbreAtual = new TimbreArcoIris();
        System.out.println("Timbre selecionado: Arco-Íris");
    }

    public void selecionarTimbreCarrinhoDeSorvete() {
        this.timbreAtual = new TimbreCarrinhoDeSorvete();
        System.out.println("Timbre selecionado: Carrinho de Sorvete");
    }

    public void selecionarTimbrePiano() {
        this.timbreAtual = new TimbrePiano();
        System.out.println("Timbre selecionado: Piano");
    }

    public void selecionarTimbrePuro() {
        this.timbreAtual = new TimbrePuro();
        System.out.println("Timbre selecionado: Puro");
    }

    public void selecionarTimbreViolao() {
        this.timbreAtual = new TimbreViolao();
        System.out.println("Timbre selecionado: Violão");
    }

    public void selecionarTimbreTrompete() {
        this.timbreAtual = new TimbreTrompete();
        System.out.println("Timbre selecionado: Trompete");
    }

    String caminhoDesktop = System.getProperty("user.home") + "/Desktop";
    String caminhoAudio = caminhoDesktop + "/Áudio.wav";

    public void gerarAudio() {
        PBL pbl = new PBL();

        // Defina os parâmetros para o experimento
        Ambiente ambiente = new Ambiente("Ar", 330);
        Fonte fonte = new Fonte("Lá do Violao", 5, 440, this.timbreAtual); // Use o timbre selecionado
        Experimento exp = new Experimento("Experimento de teste", 25,1, -10, 1, 5, 44100, ambiente, fonte);

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
