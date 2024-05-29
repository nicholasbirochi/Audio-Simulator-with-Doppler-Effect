package com.pbl.model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PBL {

    public static void main(String[] args) {
        Ambiente ambiente = new Ambiente("ar", 330);
        Timbre timbre = new TimbrePuro();
        Fonte fonte = new Fonte("Abelhinha aguda", 4, 440, timbre);
        Experimento exp = new Experimento("Experimento de teste", 25, 1, -10, 1, ambiente, fonte);

        //dados de geração do audio
        double taxaAmostragem = 800.0; //em hz
        double duracao = 5.0; //em segundos
        
        String caminhoDesktop = System.getProperty("user.home") + "/Desktop";
        Scanner scanner = new Scanner(System.in);

        try {
            String caminhoAudio = caminhoDesktop + "/audio.wav";
            File arquivoAudio = new File(caminhoAudio);
            if (arquivoAudio.exists()) {
                System.out.println("O arquivo já existe. Escolha uma opção:");
                System.out.println("1. Substituir arquivo");
                System.out.println("2. Criar com outro nome");
                System.out.print("Digite o número da opção desejada: ");
                int escolha = scanner.nextInt();
                if (escolha == 2) {
                    int contador = 1;
                    while (arquivoAudio.exists()) {
                        caminhoAudio = caminhoDesktop + "/audio (" + contador + ").wav";
                        arquivoAudio = new File(caminhoAudio);
                        contador++;
                    }
                }
            }

            exp.criarArquivoDeSimulacao(caminhoAudio, taxaAmostragem, duracao);
            System.out.println("Arquivo de áudio criado com sucesso em: " + caminhoAudio);
        } catch (IOException | UnsupportedAudioFileException e) {
            System.out.println("Erro ao criar arquivo de áudio: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }


}
