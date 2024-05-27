package com.pbl.controller;

import com.pbl.model.*;

import java.io.IOException;
import java.sql.Connection;

//import io.github.palexdev.materialfx.controls.MFXSlider;
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
//    @FXML
//    private MFXSlider sliderVelFonte;

    @FXML
    private GridPane pageTimbres;
    @FXML
    private VBox pageFonte;
    @FXML
    private VBox pageAmbiente;
    @FXML
    private VBox pageObservador;

//    @FXML
//    private MFXSlider sliderVelFonte;
//
//    double valorSlider = sliderVelFonte.getValue();


    public void showPageFonte() {
        pageTimbres.setVisible(false);
        pageFonte.setVisible(true);
        pageAmbiente.setVisible(false);
        pageObservador.setVisible(false);
    }

    public void showPageTimbres() {
        pageTimbres.setVisible(true);
        pageFonte.setVisible(false);
        pageAmbiente.setVisible(false);
        pageObservador.setVisible(false);
    }
    public void showPageAmbiente() {
        pageTimbres.setVisible(false);
        pageFonte.setVisible(false);
        pageAmbiente.setVisible(true);
        pageObservador.setVisible(false);
    }
    public void showPageObservador() {
        pageTimbres.setVisible(false);
        pageFonte.setVisible(false);
        pageAmbiente.setVisible(false);
        pageObservador.setVisible(true);
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
        // Defina os parâmetros para o experimento
        Ambiente ambiente = new Ambiente("ar", 330.0);
        Fonte fonte = new Fonte("Fonte", 4.0, 440.0, this.timbreAtual); // Use o timbre selecionado
        Experimento exp = new Experimento("Experimento de teste", 25.0, 1.0, -10.0, 1.0, 5.0, 44100, ambiente, fonte);

        try {
            exp.criarArquivoDeSimulacao(caminhoAudio);
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


