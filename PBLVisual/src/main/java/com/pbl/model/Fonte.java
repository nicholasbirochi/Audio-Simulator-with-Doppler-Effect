package com.pbl.model;

public class Fonte
{
    // Atributos:
    private double potencia;
    private double frequencia;
    private Timbre timbre;
    private String nome;
    private int id;

    // Construtor:
    public Fonte(double potencia, double frequencia, Timbre timbre)
    {
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
    
    public int getId(){
        return this.id;
    }
}
