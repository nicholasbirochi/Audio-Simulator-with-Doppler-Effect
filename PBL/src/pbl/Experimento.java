package pbl;
import java.util.List;
import java.util.ArrayList;

public class Experimento
{
  private double posicaoInicialFonte;
  private double posicaoInicialObservador;
  private double velocidadeFonte;
  private double velocidadeObservador;
  private double velocidadeSom;

  public Experimento(double posicaoInicialFonte, double posicaoInicialObservador, double velocidadeFonte, double velocidadeObservador, double velocidadeSom)
  {
    this.posicaoInicialFonte = posicaoInicialFonte;
    this.posicaoInicialObservador = posicaoInicialObservador;
    this.velocidadeFonte = velocidadeFonte;
    this.velocidadeObservador = velocidadeObservador;
    this.velocidadeSom = velocidadeSom;
  }

  public double getPosicaoInicialFonte(){
    return this.posicaoInicialFonte;
  }

  public double getPosicaoInicialObservador(){
    return this.posicaoInicialObservador;
  }

  public double getVelocidadeFonte(){
    return this.velocidadeFonte;
  }

  public double getVelocidadeObservador(){
    return this.velocidadeObservador;
  }
  
  public double getVelocidadeSom(){
    return this.velocidadeSom;
  }
  
    
}
