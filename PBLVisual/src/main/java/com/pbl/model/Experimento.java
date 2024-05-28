package com.pbl.model;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Experimento
{
  private String nome;

  private double posicaoInicialFonte;
  private double distanciaLateral;
  private double velocidadeFonte;
  private double velocidadeObservador;

  private Fonte fonte;
  private Ambiente ambiente;
  private double tempoDuracao = 5;
  private int taxaAmostragem = 44100;


  public Experimento(String nome, double posicaoInicialFonte, double distanciaLateral, double velocidadeFonte, double velocidadeObservador, Ambiente ambiente, Fonte fonte)
  {
    this.nome = nome;
    this.posicaoInicialFonte = posicaoInicialFonte;
    this.distanciaLateral = distanciaLateral;
    this.velocidadeFonte = velocidadeFonte;
    this.velocidadeObservador = velocidadeObservador;
    this.ambiente = ambiente;
    this.fonte = fonte;
  }

  public double getPosicaoInicialFonte(){
    return this.posicaoInicialFonte;
  }

  public double getDistanciaLateral(){
    return this.distanciaLateral;
  }

  public double getVelocidadeFonte(){
    return this.velocidadeFonte;
  }

  public double getVelocidadeObservador(){
    return this.velocidadeObservador;
  }

  public double getTempoDuracao(){
    return this.tempoDuracao;
  }

  public int getTaxaAmostragem(){
    return this.taxaAmostragem;
  }

  public Fonte getFonte(){
    return this.fonte;
  }

  public String getNome(){
    return this.nome;
  }

  public Ambiente getAmbiente(){
    return this.ambiente;
  }
  
    public void criarArquivoDeSimulacao(String caminhoArquivo)
          throws IOException, UnsupportedAudioFileException {

      List<Double[]> amplitudesFrequencias = Fisica.amplitudeFrequenciaDoSom(this);
      int taxaAmostragem = this.getTaxaAmostragem();
      int numAmostras = amplitudesFrequencias.size();
      Timbre timbre = this.getFonte().getTimbre();

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
