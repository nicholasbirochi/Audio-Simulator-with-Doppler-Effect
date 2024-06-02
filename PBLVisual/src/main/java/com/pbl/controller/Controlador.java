package com.pbl.controller;

import com.pbl.model.*;
import com.pbl.DAO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.io.IOException;
import java.sql.Connection;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
import java.sql.SQLException;

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
    private Label feedbackFonte;
    @FXML
    private Label feedbackAmbiente;
    @FXML
    private Label feedbackExperimento;
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
    @FXML
    private TextField velSomAmbiente;
    @FXML
    private TextField nomeAmbiente;
    @FXML
    private TextField pos0Fonte;
    @FXML
    private TextField posLateralFonte;
    @FXML
    private TextField vel0Fonte;
    @FXML
    private TextField pos0Observador;
    @FXML
    private TextField velObservador;
    @FXML
    private TextField nomeExperimento;

    private int taxa = 44100; // valor padrão
    String nomeTimbre = "TimbrePuro";
    Fonte fonte = new Fonte("Nome",5,5, new TimbrePuro());

    ConexaoBD conx = new ConexaoBD();
    Connection connection = conx.getConexao();
    DAO dao = new DAO(connection);

    public void adicionarFonte() throws SQLException {
        String nome = nomeFonte.getText();
        try {
            double potencia = Double.parseDouble(potFonte.getText());
            double frequencia = Double.parseDouble(freqFonte.getText());
            Timbre timbre = this.timbreAtual;
            String timbreString = timbre.toString(); // Obtém a string do timbre
            Fonte fonte = new Fonte(nome, potencia, frequencia, timbre);
            dao.adicionaFonte(fonte);
            System.out.println("Timbre: " + timbreString); // Se quiser exibir a string do timbre
            preencherComboBoxFontes();
            updateFeedbackFonte("Fonte criada com sucesso!", "white");
        } catch (Exception e) {
            updateFeedbackFonte("Não foi possível criar a fonte. Tente trocar o nome da fonte.", "red");
        }
    }

    // Método para atualizar o label com animação
    private void updateFeedbackFonte(String message, String color) {
        feedbackFonte.setText(message);
        feedbackFonte.setStyle("-fx-text-fill: " + color + ";");

        // Crie a transição de fade-in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), feedbackFonte);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Crie a transição de fade-out após um atraso
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), feedbackFonte);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(5)); // Espera 5 segundos antes de iniciar o fade-out

        // Quando o fade-in terminar, inicie o fade-out
        fadeIn.setOnFinished(event -> fadeOut.play());

        // Inicie o fade-in
        fadeIn.play();
    }




    public void adicionarAmbiente() throws SQLException {
        try {
        String nome = nomeAmbiente.getText();
        double velocidade = Double.parseDouble(velSomAmbiente.getText());
        dao.adicionaAmbiente(new Ambiente(nome, velocidade));
        updateFeedbackAmbienteLabel("Ambiente criado com sucesso!", "white");
        preencherComboBoxAmbientes();
    } catch (SQLException e) {
        updateFeedbackAmbienteLabel("Erro ao criar ambiente. Tente trocar o nome do ambiente.", "red");
    }
    }

    private void updateFeedbackAmbienteLabel(String message, String color) {
        feedbackAmbiente.setText(message);
        feedbackAmbiente.setStyle("-fx-text-fill: " + color + ";");

        // Crie a transição de fade-in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), feedbackAmbiente);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Crie a transição de fade-out após um atraso
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), feedbackAmbiente);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(5)); // Espera 5 segundos antes de iniciar o fade-out

        // Quando o fade-in terminar, inicie o fade-out
        fadeIn.setOnFinished(event -> fadeOut.play());

        // Inicie o fade-in
        fadeIn.play();
    }

    public void adicionarExperimento() throws SQLException {
        try {
            // Pega os valores dos campos de texto
            String nome = nomeExperimento.getText();
            Double velocidadeObservador = Double.parseDouble(velObservador.getText());
            Double posicaoLateral = Double.parseDouble(posLateralFonte.getText());
            Double velocidadeFonte = Double.parseDouble(vel0Fonte.getText());
            Double posicaoInicialFonte = Double.parseDouble(pos0Fonte.getText());

            // Pega os itens selecionados na ComboBox de fontes e ambientes
            String nomeFonteSelecionada = comboBoxFontes.getSelectionModel().getSelectedItem();
            String nomeAmbienteSelecionado = comboBoxAmbientes.getSelectionModel().getSelectedItem();
            System.out.println("[" + nomeFonteSelecionada + "]" + "[" + nomeAmbienteSelecionado + "]");

            // Busca a Fonte e o Ambiente selecionados no banco de dados
            Fonte fonteSelecionada = dao.buscarFontePorNome(nomeFonteSelecionada);
            Ambiente ambienteSelecionado = dao.buscarAmbientePorNome(nomeAmbienteSelecionado);

            // Check if fonteSelecionada is null
            if (fonteSelecionada == null) {
                System.out.println("Fonte not found in the database");
                return;
            }

            System.out.println("[" + fonteSelecionada + "]" + "[" + ambienteSelecionado + "]");

            // Cria um novo experimento com os valores obtidos
            Experimento experimento = new Experimento(
                    nome,
                    posicaoInicialFonte,
                    posicaoLateral,
                    velocidadeFonte,
                    velocidadeObservador,
                    ambienteSelecionado,
                    fonteSelecionada
            );

            // Adiciona o experimento ao banco de dados
            dao.adicionaExperimento(experimento);
            updateFeedbackExperimentoLabel("Experimento criado com sucesso!", "white");
            preencherComboBoxExperimento();
        } catch (SQLException e) {
            updateFeedbackExperimentoLabel("Erro ao criar experimento. Tente trocar o nome do experimento.", "red");
        }
    }

    private void updateFeedbackExperimentoLabel(String message, String color) {
        feedbackExperimento.setText(message);
        feedbackExperimento.setStyle("-fx-text-fill: " + color + ";");

        // Crie a transição de fade-in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), feedbackExperimento);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Crie a transição de fade-out após um atraso
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), feedbackExperimento);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(5)); // Espera 5 segundos antes de iniciar o fade-out

        // Quando o fade-in terminar, inicie o fade-out
        fadeIn.setOnFinished(event -> fadeOut.play());

        // Inicie o fade-in
        fadeIn.play();
    }

    @FXML
    private ComboBox<String> comboBoxFontes;

    public void preencherComboBoxFontes() {
        try {
            // Limpa a ComboBox
            comboBoxFontes.getItems().clear();

            // Busca os nomes das fontes do banco de dados
            List<String> nomesFontes = dao.fontesTodosNomes();

            // Adiciona os nomes das fontes à ComboBox
            comboBoxFontes.getItems().addAll(nomesFontes);
        } catch (Exception e) {
            System.out.println("Erro ao preencher ComboBox: " + e.getMessage());
        }
    }

    @FXML
    private ComboBox<String> comboBoxAmbientes;
    public void preencherComboBoxAmbientes() {
        try {
            // Limpa a ComboBox
            comboBoxAmbientes.getItems().clear();

            // Busca os nomes dos ambientes do banco de dados
            List<String> nomesAmbientes = dao.ambientesTodosNomes();

            // Adiciona os nomes dos ambientes à ComboBox
            comboBoxAmbientes.getItems().addAll(nomesAmbientes);
        } catch (Exception e) {
            System.out.println("Erro ao preencher ComboBox de Ambientes: " + e.getMessage());
        }
    }

    @FXML
    private ComboBox<String> comboBoxExperimento;

    public void preencherComboBoxExperimento() {
        try {
            // Limpa a ComboBox
            comboBoxExperimento.getItems().clear();

            // Busca os nomes dos Experimento do banco de dados
            List<String> nomesExperimentos = dao.experimentosTodosNomes();

            // Adiciona os nomes dos ambientes à ComboBox
            comboBoxExperimento.getItems().addAll(nomesExperimentos);
        } catch (Exception e) {
            System.out.println("Erro ao preencher ComboBox de Experimentos: " + e.getMessage());
        }
    }


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
                // Verifica se o novo valor corresponde a um número decimal positivo válido
                if (!newValue.matches("\\d*(\\.\\d{0,4})?") || newValue.startsWith("-")) {
                    // Remove caracteres não numéricos ou negativos
                    freqFonte.setText(oldValue);
                }
            }
        });

        velSomAmbiente.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Verifica se o novo valor corresponde a um número decimal positivo válido
                if (!newValue.matches("\\d*(\\.\\d{0,4})?") || newValue.startsWith("-")) {
                    // Remove caracteres não numéricos ou negativos
                    velSomAmbiente.setText(oldValue);
                }
            }
        });

        pos0Fonte.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Verifica se o novo valor corresponde a um número decimal válido com sinal
                if (!newValue.matches("-?\\d*(\\.\\d{0,4})?")) {
                    // Remove caracteres não numéricos
                    pos0Fonte.setText(oldValue);
                }
            }
        });
        posLateralFonte.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Verifica se o novo valor corresponde a um número decimal válido com sinal
                if (!newValue.matches("-?\\d*(\\.\\d{0,4})?")) {
                    // Remove caracteres não numéricos
                    posLateralFonte.setText(oldValue);
                }
            }
        });
        vel0Fonte.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Verifica se o novo valor corresponde a um número decimal válido com sinal
                if (!newValue.matches("-?\\d*(\\.\\d{0,4})?")) {
                    // Remove caracteres não numéricos
                    vel0Fonte.setText(oldValue);
                }
            }
        });
        pos0Observador.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Verifica se o novo valor corresponde a um número decimal válido com sinal
                if (!newValue.matches("-?\\d*(\\.\\d{0,4})?")) {
                    // Remove caracteres não numéricos
                    pos0Observador.setText(oldValue);
                }
            }
        });
        velObservador.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Verifica se o novo valor corresponde a um número decimal válido com sinal
                if (!newValue.matches("-?\\d*(\\.\\d{0,4})?")) {
                    // Remove caracteres não numéricos
                    velObservador.setText(oldValue);
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
        preencherComboBoxFontes();
        preencherComboBoxAmbientes();
        preencherComboBoxExperimento();
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
        try {
            // Pega o nome do experimento selecionado na ComboBox
            String nomeExperimentoSelecionado = comboBoxExperimento.getSelectionModel().getSelectedItem();

            // Busca o experimento do banco de dados pelo nome
            Experimento exp = dao.buscarExperimentoPorNome(nomeExperimentoSelecionado);

            // Verifica se o experimento foi encontrado
            if (exp == null) {
                System.out.println("Experimento não encontrado: " + nomeExperimentoSelecionado);
                return;
            }

            // Cria o arquivo de simulação com o experimento obtido
            exp.criarArquivoDeSimulacao(caminhoAudio, taxa, duracao);

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
    private void playTimbreAudio(String timbreFileName) {
        try {
            // Carrega o arquivo de áudio do timbre
            FileInputStream audioFile = new FileInputStream("./src/main/resources/timbre/" + timbreFileName);
            if (audioFile == null) {
                System.out.println("Arquivo de áudio não encontrado: " + timbreFileName);
                return;
            }

            // Cria um fluxo de áudio a partir do arquivo
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Obtém um clip de áudio e abre o fluxo de áudio
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);

            // Reproduz o áudio
            audioClip.start();

            System.out.println("Reproduzindo áudio: " + timbreFileName);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("O formato de arquivo de áudio não é suportado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de áudio: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println("Linha de áudio não disponível: " + e.getMessage());
        }
    }
    public void playAbelhaAudio() {
        playTimbreAudio("puro.wav");
    }
}

