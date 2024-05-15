package pbl;
import java.util.List;
import java.util.ArrayList;

public class Experimento
{
  private double posicaoInicialFonte;
  private double velocidadeFonte;
  private double velocidadeObservador;
  private double velocidadeSom;
  private double tempoDuracao;
  private int taxaAmostragem;
  private Fonte fonte;

  public Experimento(double posicaoInicialFonte, double velocidadeFonte, double velocidadeObservador, double velocidadeSom, double tempoDuracao, int taxaAmostragem, Fonte fonte)
  {
    this.posicaoInicialFonte = posicaoInicialFonte;
    this.velocidadeFonte = velocidadeFonte;
    this.velocidadeObservador = velocidadeObservador;
    this.velocidadeSom = velocidadeSom;
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
  
  public double getVelocidadeSom(){
    return this.velocidadeSom;
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
    
}
