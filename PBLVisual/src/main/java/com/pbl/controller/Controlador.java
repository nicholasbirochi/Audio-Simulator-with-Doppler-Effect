package com.pbl.controller;

import com.pbl.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    protected void onButtonGerarClick() {
        System.out.println("Botão Gerar foi pressionado!");
        try {
            Ambiente ambiente = new Ambiente(1, "ar", 330);
            Timbre timbre = new TimbreAbelhaEletrica();
            Fonte fonte = new Fonte(4, 440, timbre);
            Experimento exp = new Experimento(1, "Experimento de teste", 25, -10, 1, 5, 44100, ambiente, fonte);
            String caminhoDesktop = System.getProperty("user.home") + "/Desktop";
            PBL.criarArquivo(caminhoDesktop + "/Audio.wav", exp);
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
