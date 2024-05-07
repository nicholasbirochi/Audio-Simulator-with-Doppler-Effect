package pbl;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PBL
{
    // Mapeamento de notas musicais para suas frequências em Hz
    private static final Map<String, Double> MAPA_FREQUENCIA_NOTAS = new HashMap<>();
    static
    {
        MAPA_FREQUENCIA_NOTAS.put("Dó", 261.63);
        MAPA_FREQUENCIA_NOTAS.put("Ré", 293.66);
        MAPA_FREQUENCIA_NOTAS.put("Mi", 329.63);
        MAPA_FREQUENCIA_NOTAS.put("Fá", 349.23);
        MAPA_FREQUENCIA_NOTAS.put("Sol", 392.00);
        MAPA_FREQUENCIA_NOTAS.put("Lá", 440.00);
        MAPA_FREQUENCIA_NOTAS.put("Si", 493.88);
    }
    
    public static void main(String[] args)
    {
        System.out.println(Calculo.seno(Math.PI/2));

        System.out.println(seno(Math.PI));
        
        // Defina os parâmetros para o arquivo de áudio
            int taxaAmostragem = 44100; // Frequência de amostragem (Hz)
            int duracaoSegundos = 5; // Duração do áudio em segundos
            double amplitude = 0.5; // Amplitude do som (0.0 a 1.0)

            // Caminho do arquivo de áudio a ser salvo
            String caminhoAudio = "Z:\\audio.wav";

            // Crie o arquivo de áudio
            try
            {
                Scanner scanner = new Scanner(System.in);

                // Exibir o menu e obter a nota musical desejada do usuário
                System.out.println("Escolha a nota musical:");
                System.out.println("1. Dó");
                System.out.println("2. Ré");
                System.out.println("3. Mi");
                System.out.println("4. Fá");
                System.out.println("5. Sol");
                System.out.println("6. Lá");
                System.out.println("7. Si");
                System.out.print("Digite o número da opção desejada: ");
                int opcao = scanner.nextInt();

                String nota;
                switch (opcao)
                {
                    case 1:
                        nota = "Dó";
                        break;
                    case 2:
                        nota = "Ré";
                        break;
                    case 3:
                        nota = "Mi";
                        break;
                    case 4:
                        nota = "Fá";
                        break;
                    case 5:
                        nota = "Sol";
                        break;
                    case 6:
                        nota = "Lá";
                        break;
                    case 7:
                        nota = "Si";
                        break;
                    default:
                        System.out.println("Opção inválida. Escolha uma opção de 1 a 7.");
                        return;
                }

                criarArquivo(caminhoAudio, taxaAmostragem, duracaoSegundos, nota, amplitude);
                System.out.println("Arquivo de áudio criado com sucesso em: " + caminhoAudio);
            }
            catch (IOException | UnsupportedAudioFileException e)
            {
                System.out.println("Erro: " + e);
            }
        }

        public static void criarArquivo(String caminhoArquivo, int taxaAmostragem, int duracaoSegundos, String nota, double amplitude)
                throws IOException, UnsupportedAudioFileException
        {
            // Frequência correspondente à nota musical
            double frequenciaHz = MAPA_FREQUENCIA_NOTAS.getOrDefault(nota, 0.0);

            // Calcule o número total de amostras
            int numAmostras = taxaAmostragem * duracaoSegundos;

            // Crie o buffer para armazenar os dados do áudio
            byte[] dadosAudio = new byte[2 * numAmostras]; // 2 bytes por amostra (formato de áudio PCM de 16 bits)

            // Preencha o buffer com os dados do áudio
            for (int i = 0; i < numAmostras; i++)
            {
                double tempo = i / (double) taxaAmostragem;
                double valor = amplitude * Math.sin(2 * Math.PI * frequenciaHz * tempo);

                // Converte o valor para formato PCM de 16 bits e armazena no buffer
                short amostra = (short) (valor * Short.MAX_VALUE);
                dadosAudio[2 * i] = (byte) (amostra & 0xFF);
                dadosAudio[2 * i + 1] = (byte) ((amostra >> 8) & 0xFF);
            }

            // Escreva os dados do áudio para um arquivo WAV
            AudioFormat formato = new AudioFormat(taxaAmostragem, 16, 1, true, false);
            AudioInputStream entradaAudio = new AudioInputStream(
                    new java.io.ByteArrayInputStream(dadosAudio), formato, numAmostras);
            AudioSystem.write(entradaAudio, AudioFileFormat.Type.WAVE, new File(caminhoArquivo));
    }
}
