package pbl;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PBL {

    public static void main(String[] args) {
        Fonte f = new Fonte(5, 440);
        Experimento exp = new Experimento(10, -2, 1, 440, 5, 44100, f);
        double[] frequencias = Fisica.frequenciasAntesDepois(exp);
        List<Double> intensidades = Fisica.intensidadeSom(exp);
        
        String caminhoDesktop = System.getProperty("user.home") + "/Desktop";
        Scanner scanner = new Scanner(System.in);

        try {
            String caminhoAudio = caminhoDesktop + '/' + nota + ".wav";
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
                        caminhoAudio = caminhoDesktop + '/' + nota + "(" + contador + ").wav";
                        arquivoAudio = new File(caminhoAudio);
                        contador++;
                    }
                }
            }

            criarArquivo(caminhoAudio, exp);
            System.out.println("Arquivo de áudio criado com sucesso em: " + caminhoAudio);
        } catch (IOException | UnsupportedAudioFileException e) {
            System.out.println("Erro ao criar arquivo de áudio: " + e.getMessage());
        }
    }

    public static void criarArquivo(String caminhoArquivo, Experimento exp)
            throws IOException, UnsupportedAudioFileException {

        double[] frequencias = Fisica.frequenciasAntesDepois(exp);
        List<Double> intensidadesFrequencias = Fisica.intensidadeFrequenciaDoSom(exp);
        Double maiorIntensidade = Calculo.acharMax(intensidadesFrequencias);
        int taxaAmostragem = exp.getTaxaAmostragem;
        int numAmostras = intensidades.size();

        byte[] dadosAudio = new byte[2 * numAmostras];
        int i = 0;
        foreach (double[] inf : intensidadesFrequencias) {
            double tempo = i / (double) taxaAmostragem;
            double valor = (inf[0]/maiorIntensidade) * Calculo.seno(2 * Math.PI * inf[1] * tempo);
            short amostra = (short) (valor * Short.MAX_VALUE);
            dadosAudio[2 * i] = (byte) (amostra & 0xFF);
            dadosAudio[2 * i + 1] = (byte) ((amostra >> 8) & 0xFF);
            i += 1;
        }
        AudioFormat formato = new AudioFormat(taxaAmostragem, 16, 1, true, false);
        AudioInputStream entradaAudio = new AudioInputStream(new java.io.ByteArrayInputStream(dadosAudio), formato, numAmostras);
        AudioSystem.write(entradaAudio, AudioFileFormat.Type.WAVE, new File(caminhoArquivo));
    }
}
