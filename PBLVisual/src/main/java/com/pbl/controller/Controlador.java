package com.pbl.controller;

import com.pbl.model.*;
import com.pbl.DAO;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;

public class Controlador {

    @FXML
    private HBox topBar;
    @FXML
    private void handleCloseButtonAction() {
        Stage stage = (Stage) topBar.getScene().getWindow();
        stage.close();
    }
    @FXML
    private VBox pageHome;
    @FXML
    private VBox pageTimbres;
    @FXML
    private VBox pageFonte;
    @FXML
    private VBox pageAmbiente;
    @FXML
    private VBox pageExperimento;
    @FXML
    private VBox pageGerarAudio;
    @FXML
    private Label selectedTimbreLabel;
    @FXML
    private Label selectedTimbreLabelFonte;
    @FXML
    private TextField duracaoAudio;
    private double duracao = 5.0;
    @FXML
    private TextField taxaAmostragem;
    @FXML
    private TextField nomeFonte;
    @FXML
    private TextField potFonte;
    @FXML
    private TextField freqFonte;

    private int taxa = 44100; // valor padrão
    String nomeTimbre = "TimbrePuro";
    Fonte fonte = new Fonte("Nome",5,5, new TimbrePuro());

//    ConexaoBD conx = new ConexaoBD();
//    Connection connection = conx.getConexao();
//    DAO dao = new DAO(connection);
//
//    dao.adicionaFonte(fonte);
//

    private double xOffset = 0;
    private double yOffset = 0;

    private Timbre timbreAtual = new TimbrePuro();

    @FXML
    public void initialize() {

        // Adiciona um ouvinte para a propriedade textProperty
        duracaoAudio.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Verifica se o novo valor corresponde a um número decimal válido com até 4 casas decimais
                if (!newValue.matches("\\d*(\\.\\d{0,4})?|\\d*(,\\d{0,4})?")) {
                    // Remove caracteres não numéricos
                    duracaoAudio.setText(oldValue);
                }

                // Limita o valor máximo da TextField para 120
                if (!newValue.isEmpty()) {
                    try {
                        double valor = Double.parseDouble(newValue.replace(',', '.')); // substitui vírgula por ponto
                        if (valor > 120) {
                            duracaoAudio.setText(oldValue);
                        } else {
                            // Atualiza o valor da duração
                            duracao = valor;
                        }
                    } catch (NumberFormatException e) {
                        duracaoAudio.setText(oldValue);
                    }
                }
            }
        });

        // Adiciona um ouvinte para a propriedade textProperty da taxaAmostragem
        taxaAmostragem.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Verifica se o novo valor corresponde a um número inteiro válido
                if (!newValue.matches("\\d*")) {
                    // Remove caracteres não numéricos
                    taxaAmostragem.setText(oldValue);
                }

                // Atualiza a variável de taxa de amostragem se o novo valor não estiver vazio
                if (!newValue.isEmpty()) {
                    try {
                        int valor = Integer.parseInt(newValue);
                        taxa = valor;
                    } catch (NumberFormatException e) {
                        taxaAmostragem.setText(oldValue);
                    }
                }
            }
        });

        // Adiciona ouvintes para a propriedade textProperty de potFonte e freqFonte
        potFonte.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Verifica se o novo valor corresponde a um número decimal positivo válido
                if (!newValue.matches("\\d*(\\.\\d{0,4})?") || newValue.startsWith("-")) {
                    // Remove caracteres não numéricos ou negativos
                    potFonte.setText(oldValue);
                }
            }
        });

        freqFonte.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Verifica se o novo valor corresponde a um número decimal válido com sinal
                if (!newValue.matches("-?\\d*(\\.\\d{0,4})?")) {
                    // Remove caracteres não numéricos
                    freqFonte.setText(oldValue);
                }
            }
        });


        topBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        topBar.setOnMouseDragged(event -> {
            Stage stage = (Stage) topBar.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        // Atualize os labels com o timbre padrão
        updateTimbreLabel("Puro");
        selectedTimbreLabelFonte.setText("Timbre selecionado: Puro");


    }

    // Método para atualizar o label com animação
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

    // Método para trocar de página com animação
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

    // Métodos para exibir cada página
    public void showPageHome() {
        switchPage(getVisiblePage(), pageHome);
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

    public void showPageGerarAudio() {
        switchPage(getVisiblePage(), pageGerarAudio);
    }

    // Método auxiliar para obter a página atualmente visível
    private VBox getVisiblePage() {
        if (pageHome.isVisible()) return pageHome;
        if (pageTimbres.isVisible()) return pageTimbres;
        if (pageFonte.isVisible()) return pageFonte;
        if (pageAmbiente.isVisible()) return pageAmbiente;
        if (pageExperimento.isVisible()) return pageExperimento;
        return pageGerarAudio;
    }

    // Métodos para selecionar timbres
    public void selecionarTimbreAbelhaEletrica() {
        this.timbreAtual = new TimbreAbelhaEletrica();
        System.out.println("Timbre selecionado: Abelha Elétrica");
        updateTimbreLabel("Abelha Elétrica");
        selectedTimbreLabelFonte.setText("Timbre selecionado: Abelha Elétrica");
    }

    public void selecionarTimbreAlienigena() {
        this.timbreAtual = new TimbreAlienigena();
        System.out.println("Timbre selecionado: Alienígena");
        updateTimbreLabel("Alienígena");
        selectedTimbreLabelFonte.setText("Timbre selecionado: Alienígena");
    }

    public void selecionarTimbreArcoIris() {
        this.timbreAtual = new TimbreArcoIris();
        System.out.println("Timbre selecionado: Arco-Íris");
        updateTimbreLabel("Arco-Íris");
        selectedTimbreLabelFonte.setText("Timbre selecionado: Arco-Íris");
    }

    public void selecionarTimbreCarrinhoDeSorvete() {
        this.timbreAtual = new TimbreCarrinhoDeSorvete();
        System.out.println("Timbre selecionado: Carrinho de Sorvete");
        updateTimbreLabel("Carrinho de Sorvete");
        selectedTimbreLabelFonte.setText("Timbre selecionado: Carrinho de Sorvete");
    }

    public void selecionarTimbrePiano() {
        this.timbreAtual = new TimbrePiano();
        System.out.println("Timbre selecionado: Piano");
        updateTimbreLabel("Piano");
        selectedTimbreLabelFonte.setText("Timbre selecionado: Piano");
    }

    public void selecionarTimbrePuro() {
        this.timbreAtual = new TimbrePuro();
        System.out.println("Timbre selecionado: Puro");
        updateTimbreLabel("Puro");
        selectedTimbreLabelFonte.setText("Timbre selecionado: Puro");
    }

    public void selecionarTimbreViolao() {
        this.timbreAtual = new TimbreViolao();
        System.out.println("Timbre selecionado: Violão");
        updateTimbreLabel("Violão");
        selectedTimbreLabelFonte.setText("Timbre selecionado: Violão");
    }

    public void selecionarTimbreTrompete() {
        this.timbreAtual = new TimbreTrompete();
        System.out.println("Timbre selecionado: Trompete");
        updateTimbreLabel("Trompete");
        selectedTimbreLabelFonte.setText("Timbre selecionado: Trompete");
    }

    String caminhoDesktop = System.getProperty("user.home") + "/Desktop";
    String caminhoAudio = caminhoDesktop + "/Áudio.wav";

    // Método para gerar áudio
    public void gerarAudio() {
        // Defina os parâmetros para o experimento
        Ambiente ambiente = new Ambiente("ar", 330.0);
        Fonte fonte = new Fonte("Fonte", 4.0, 440.0, this.timbreAtual); // Use o timbre selecionado
        Experimento exp = new Experimento("Experimento de teste", 25.0, 1.0, -10.0, 1.0, ambiente, fonte);

        try {
            exp.criarArquivoDeSimulacao(caminhoAudio, taxa, duracao); // Use o valor da duração
            System.out.println("Arquivo de áudio criado com sucesso em: " + caminhoAudio);
        } catch (IOException | UnsupportedAudioFileException e) {
            System.out.println("Erro ao criar arquivo de áudio: " + e.getMessage());
        }
    }

    // Método para reproduzir o áudio usando um caminho predefinido
    public void playAudioFile() {
        try {
            // Abre o arquivo de áudio
            File audioFile = new File(caminhoAudio);
            if (!audioFile.exists()) {
                System.out.println("Arquivo de áudio não encontrado: " + caminhoAudio);
                return;
            }

            // Cria um fluxo de áudio a partir do arquivo
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Obtém um clip de áudio e abre o fluxo de áudio
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);

            // Reproduz o áudio
            audioClip.start();

            System.out.println("Reproduzindo áudio: " + caminhoAudio);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("O formato de arquivo de áudio não é suportado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de áudio: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println("Linha de áudio não disponível: " + e.getMessage());
        }
    }
}
