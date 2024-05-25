package com.pbl.controller;

import com.pbl.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Controlador {
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView imageTimbreAbelhaEletrica;
    @FXML
    private ImageView imageTimbreAlienigena;
    // Adicionar outros ImageView para os timbres
    @FXML
    private Button buttonGerar;
    @FXML
    private Slider sliderDuracao;
    private double duracaoAudio;

    @FXML
    public void initialize() {
        // Inicializa a duração do áudio com o valor inicial do slider
        duracaoAudio = sliderDuracao.getValue();

        // Adiciona um ouvinte de mudança de valor ao slider
        sliderDuracao.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                duracaoAudio = newValue.doubleValue();
                System.out.println("A duração do áudio foi alterada para: " + duracaoAudio + " segundos");
            }
        });
    }

    @FXML
    protected void onButtonGerarClick() {
        System.out.println("Botão Gerar foi pressionado!");
        try {
            Ambiente ambiente = new Ambiente(1, "ar", 330);
            Timbre timbre = new TimbreAbelhaEletrica();
            Fonte fonte = new Fonte(4, 440, timbre);
            // Use a variável duracaoAudio para definir a duração do experimento
            Experimento exp = new Experimento(1, "Experimento de teste",25, -10, 0, duracaoAudio, 44100, ambiente, fonte);
            String caminhoDesktop = System.getProperty("user.home") + "/Desktop";
            PBL.criarArquivo(caminhoDesktop + "/Audio.wav", exp);
            System.out.println("Arquivo gerado com sucesso!");
        } catch (IOException | UnsupportedAudioFileException e) {
            System.out.println("Erro ao criar arquivo de áudio: " + e.getMessage());
        }
    }

    @FXML
    protected void onTimbreAbelhaEletricaClick() {
        welcomeText.setText("Timbre Abelha Elétrica selecionado!");
    }

    @FXML
    protected void onTimbreAlienigenaClick() {
        welcomeText.setText("Timbre Alienígena selecionado!");
    }

    // Adicionar métodos para os outros botões
}
