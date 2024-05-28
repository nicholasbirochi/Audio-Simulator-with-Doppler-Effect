package com.pbl.model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
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
    
    public void tocarFonte(String caminhoArquivo)
          throws IOException, UnsupportedAudioFileException {

        Ambiente ambiente = new Ambiente("ar", 330);
        Experimento exp = new Experimento("Experimento de teste", 0, 1, 0, 0,ambiente, this);
        
        exp.criarArquivoDeSimulacao(caminhoArquivo);
    }
}
