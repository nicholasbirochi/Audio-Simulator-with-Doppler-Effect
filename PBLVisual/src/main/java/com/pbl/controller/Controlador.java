package com.pbl.controller;

import com.pbl.model.*;

import java.io.IOException;
import java.sql.Connection;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Controlador {

    // ConexaoBD conx = new ConexaoBD();
    // Connection connection = conx.getConexao();

    @FXML
    private HBox topBar;
    @FXML
    private VBox pageTimbres;
    @FXML
    private VBox pageFonte;
    @FXML
    private VBox pageAmbiente;
    @FXML
    private VBox pageExperimento;
    @FXML
    private Label selectedTimbreLabel;

    private double xOffset = 0;
    private double yOffset = 0;

    private Timbre timbreAtual = new TimbrePuro();

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

        // Atualize o label com o timbre padrão
        updateTimbreLabel("Puro");
    }

    private void updateTimbreLabel(String timbre) {
        // Crie a transição de fade-out
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), selectedTimbreLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Crie a transição de fade-in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), selectedTimbreLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Quando o fade-out terminar, atualize o texto e inicie o fade-in
        fadeOut.setOnFinished(event -> {
            selectedTimbreLabel.setText("Timbre selecionado: " + timbre);
            fadeIn.play();
        });

        // Inicie o fade-out
        fadeOut.play();
    }

    private void switchPage(VBox currentPage, VBox newPage) {
        // Crie a transição de fade-out
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), currentPage);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Crie a transição de fade-in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), newPage);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Quando o fade-out terminar, mude a página e inicie o fade-in
        fadeOut.setOnFinished(event -> {
            currentPage.setVisible(false);
            newPage.setVisible(true);
            fadeIn.play();
        });

        // Inicie o fade-out
        fadeOut.play();
    }

    public void showPageFonte() {
        switchPage(getVisiblePage(), pageFonte);
    }

    public void showPageTimbres() {
        switchPage(getVisiblePage(), pageTimbres);
    }

    public void showPageAmbiente() {
        switchPage(getVisiblePage(), pageAmbiente);
    }

    public void showPageExperimento() {
        switchPage(getVisiblePage(), pageExperimento);
    }

    private VBox getVisiblePage() {
        if (pageTimbres.isVisible()) return pageTimbres;
        if (pageFonte.isVisible()) return pageFonte;
        if (pageAmbiente.isVisible()) return pageAmbiente;
        return pageExperimento;
    }

    public void selecionarTimbreAbelhaEletrica() {
        this.timbreAtual = new TimbreAbelhaEletrica();
        System.out.println("Timbre selecionado: Abelha Elétrica");
        updateTimbreLabel("Abelha Elétrica");
    }

    public void selecionarTimbreAlienigena() {
        this.timbreAtual = new TimbreAlienigena();
        System.out.println("Timbre selecionado: Alienígena");
        updateTimbreLabel("Alienígena");
    }

    public void selecionarTimbreArcoIris() {
        this.timbreAtual = new TimbreArcoIris();
        System.out.println("Timbre selecionado: Arco-Íris");
        updateTimbreLabel("Arco-Íris");
    }

    public void selecionarTimbreCarrinhoDeSorvete() {
        this.timbreAtual = new TimbreCarrinhoDeSorvete();
        System.out.println("Timbre selecionado: Carrinho de Sorvete");
        updateTimbreLabel("Carrinho de Sorvete");
    }

    public void selecionarTimbrePiano() {
        this.timbreAtual = new TimbrePiano();
        System.out.println("Timbre selecionado: Piano");
        updateTimbreLabel("Piano");
    }

    public void selecionarTimbrePuro() {
        this.timbreAtual = new TimbrePuro();
        System.out.println("Timbre selecionado: Puro");
        updateTimbreLabel("Puro");
    }

    public void selecionarTimbreViolao() {
        this.timbreAtual = new TimbreViolao();
        System.out.println("Timbre selecionado: Violão");
        updateTimbreLabel("Violão");
    }

    public void selecionarTimbreTrompete() {
        this.timbreAtual = new TimbreTrompete();
        System.out.println("Timbre selecionado: Trompete");
        updateTimbreLabel("Trompete");
    }

    String caminhoDesktop = System.getProperty("user.home") + "/Desktop";
    String caminhoAudio = caminhoDesktop + "/Áudio.wav";

    public void gerarAudio() {
        // Defina os parâmetros para o experimento
        Ambiente ambiente = new Ambiente("ar", 330.0);
        Fonte fonte = new Fonte("Fonte", 4.0, 440.0, this.timbreAtual); // Use o timbre selecionado
        Experimento exp = new Experimento("Experimento de teste", 25.0, 1.0, -10.0, 1.0, ambiente, fonte);

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
