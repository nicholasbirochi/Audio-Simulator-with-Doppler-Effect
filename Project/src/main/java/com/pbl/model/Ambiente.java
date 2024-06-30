package com.pbl.model;

public class Ambiente
{
  private double velocidadeSom;
  private String nome;

  public Ambiente(String nome, double velocidadeSom)
  {
    this.velocidadeSom = velocidadeSom;
    this.nome = nome;
  }
  
  public double getVelocidadeSom(){
    return this.velocidadeSom;
  }

  public String getNome(){
    return this.nome;
  }
    
}
