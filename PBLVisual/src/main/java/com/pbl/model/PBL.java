package com.pbl.model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PBL {

    public static void main(String[] args) {
        Ambiente ambiente = new Ambiente(1, "ar", 330);
        Timbre timbre = new TimbreAbelhaEletrica();
        Fonte fonte = new Fonte(4, 440, timbre);
        Experimento exp = new Experimento(1, "Experimento de teste", 25, -10, 1, 5, 44100, ambiente, fonte);
        
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

            criarArquivo(caminhoAudio, exp);
            System.out.println("Arquivo de áudio criado com sucesso em: " + caminhoAudio);
        } catch (IOException | UnsupportedAudioFileException e) {
            System.out.println("Erro ao criar arquivo de áudio: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static void criarArquivo(String caminhoArquivo, Experimento exp)
            throws IOException, UnsupportedAudioFileException {

        List<Double[]> amplitudesFrequencias = Fisica.amplitudeFrequenciaDoSom(exp);   
        int taxaAmostragem = exp.getTaxaAmostragem();
        int numAmostras = amplitudesFrequencias.size();
        Timbre timbre = exp.getFonte().getTimbre();

        byte[] dadosAudio = new byte[2 * numAmostras];
        int i = 0;
        for (Double[] inf : amplitudesFrequencias) {
            double tempo = i / (double) taxaAmostragem;
            double valor = inf[0]*timbre.onda(2 * Math.PI * inf[1] * tempo);
            inf[0] = valor;
            i += 1;
        }
        
        Double maiorAmostra = Calculo.acharMax(amplitudesFrequencias);
        i = 0;
        for (Double[] inf : amplitudesFrequencias) {
            short amostra = (short) ((inf[0]/maiorAmostra) * Short.MAX_VALUE);
            dadosAudio[2 * i] = (byte) (amostra & 0xFF);
            dadosAudio[2 * i + 1] = (byte) ((amostra >> 8) & 0xFF);
            i += 1;
        }
        AudioFormat formato = new AudioFormat(taxaAmostragem, 16, 1, true, false);
        AudioInputStream entradaAudio = new AudioInputStream(new java.io.ByteArrayInputStream(dadosAudio), formato, numAmostras);
        AudioSystem.write(entradaAudio, AudioFileFormat.Type.WAVE, new File(caminhoArquivo));
    }
}
