package pbl;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PBL {
    private static final Map<String, Double> MAPA_FREQUENCIA_NOTAS = new HashMap<>();
    static {
        MAPA_FREQUENCIA_NOTAS.put("Dó", 261.63);
        MAPA_FREQUENCIA_NOTAS.put("Ré", 293.66);
        MAPA_FREQUENCIA_NOTAS.put("Mi", 329.63);
        MAPA_FREQUENCIA_NOTAS.put("Fá", 349.23);
        MAPA_FREQUENCIA_NOTAS.put("Sol", 392.00);
        MAPA_FREQUENCIA_NOTAS.put("Lá", 440.00);
        MAPA_FREQUENCIA_NOTAS.put("Si", 493.88);
    }

    public static void main(String[] args) {
        int taxaAmostragem = 44100; // Frequência de amostragem (Hz)
        int duracaoSegundos = 5; // Duração do áudio em segundos
        double amplitude = 0.5; // Amplitude do som (0.0 a 1.0)
        String caminhoDesktop = System.getProperty("user.home") + "/Desktop";
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Escolha a nota musical:");
            System.out.println("1. Dó\n2. Ré\n3. Mi\n4. Fá\n5. Sol\n6. Lá\n7. Si");
            System.out.print("Digite o número da opção desejada: ");
            int opcao = scanner.nextInt();
            String nota = switch (opcao) {
                case 1 -> "Dó";
                case 2 -> "Ré";
                case 3 -> "Mi";
                case 4 -> "Fá";
                case 5 -> "Sol";
                case 6 -> "Lá";
                case 7 -> "Si";
                default -> throw new IllegalStateException("Opção inválida. Escolha uma opção de 1 a 7.");
            };

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

            criarArquivo(caminhoAudio, taxaAmostragem, duracaoSegundos, nota, amplitude);
            System.out.println("Arquivo de áudio criado com sucesso em: " + caminhoAudio);
        } catch (IOException | UnsupportedAudioFileException e) {
            System.out.println("Erro ao criar arquivo de áudio: " + e.getMessage());
        }
    }

    public static void criarArquivo(String caminhoArquivo, int taxaAmostragem, int duracaoSegundos, String nota, double amplitude)
            throws IOException, UnsupportedAudioFileException {
        double frequenciaHz = MAPA_FREQUENCIA_NOTAS.getOrDefault(nota, 0.0);
        int numAmostras = taxaAmostragem * duracaoSegundos;
        byte[] dadosAudio = new byte[2 * numAmostras]; // 2 bytes por amostra (formato de áudio PCM de 16 bits)
        for (int i = 0; i < numAmostras; i++) {
            double tempo = i / (double) taxaAmostragem;
            double valor = amplitude * Math.sin(2 * Math.PI * frequenciaHz * tempo);
            short amostra = (short) (valor * Short.MAX_VALUE);
            dadosAudio[2 * i] = (byte) (amostra & 0xFF);
            dadosAudio[2 * i + 1] = (byte) ((amostra >> 8) & 0xFF);
        }
        AudioFormat formato = new AudioFormat(taxaAmostragem, 16, 1, true, false);
        AudioInputStream entradaAudio = new AudioInputStream(new java.io.ByteArrayInputStream(dadosAudio), formato, numAmostras);
        AudioSystem.write(entradaAudio, AudioFileFormat.Type.WAVE, new File(caminhoArquivo));
    }
}
