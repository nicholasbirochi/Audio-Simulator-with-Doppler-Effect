package com.pbl.model;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Fonte
{
    // Atributos:
    private final double potencia;
    private final double frequencia;
    private final Timbre timbre;
    private final String nome;

    // Construtor:
    public Fonte(String nome, double potencia, double frequencia, Timbre timbre)
    {
        this.nome = nome;
        this.potencia = potencia;
        this.frequencia = frequencia;
        this.timbre = timbre;
    }

    // Getters:
    public double getPotencia()
    {
        return potencia;
    }
    
    public double getFrequencia()
    {
        return frequencia;
    }
    
    public Timbre getTimbre(){
        return timbre;
    }

    public String getNome(){
        return this.nome;
    }
    
    public void tocarFonte(String caminhoArquivo, double taxaAmostragem, double duracao)
          throws IOException, UnsupportedAudioFileException {

        Ambiente ambiente = new Ambiente("ar", 330);
        Experimento exp = new Experimento("Experimento de teste", 0, 1, 0, 0,ambiente, this);
        
        exp.criarArquivoDeSimulacao(caminhoArquivo, taxaAmostragem, duracao);
    }
}
