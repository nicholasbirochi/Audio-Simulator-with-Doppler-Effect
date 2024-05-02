package pbl;

public class Fonte
{
    // Atributos:
    private double potencia;
    private double frequencia;

    // Construtor:
    public Fonte(double potencia, double frequencia)
    {
        this.potencia = potencia;
        this.frequencia = frequencia;
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
}
