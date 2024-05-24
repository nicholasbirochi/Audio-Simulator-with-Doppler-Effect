package com.pbl.model;
import java.util.List;
import java.util.ArrayList;

public class Experimento
{
  private String nome;
  private int id;
  
  private double posicaoInicialFonte;
  private double velocidadeFonte;
  private double velocidadeObservador;
  private double tempoDuracao;
  private int taxaAmostragem;
  
  private Fonte fonte;
  private Ambiente ambiente;
  

  public Experimento(int id, String nome, double posicaoInicialFonte, double velocidadeFonte, double velocidadeObservador, double tempoDuracao, int taxaAmostragem, Ambiente ambiente, Fonte fonte)
  {
    this.id = id;
    this.nome = nome;
    this.posicaoInicialFonte = posicaoInicialFonte;
    this.velocidadeFonte = velocidadeFonte;
    this.velocidadeObservador = velocidadeObservador;
    this.ambiente = ambiente;
    this.tempoDuracao = tempoDuracao;
    this.taxaAmostragem = taxaAmostragem;
    this.fonte = fonte;
  }

  public double getPosicaoInicialFonte(){
    return this.posicaoInicialFonte;
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

  public int getId(){
    return this.id;
  }

  public Ambiente getAmbiente(){
    return this.ambiente;
  }
    
}
