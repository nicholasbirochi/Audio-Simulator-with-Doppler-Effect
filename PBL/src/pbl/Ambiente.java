package pbl;
import java.util.List;
import java.util.ArrayList;

public class Ambiente
{
  private double velocidadeSom;
  private String nome;
  private int id;

  public Ambiente(int id, String nome, double velocidadeSom)
  {
    this.velocidadeSom = velocidadeSom;
    this.nome = nome;
    this.id = id;
  }
  
  public double getVelocidadeSom(){
    return this.velocidadeSom;
  }

  public String getNome(){
    return this.nome;
  }

  public int getId(){
    return this.id;
  }
    
}
