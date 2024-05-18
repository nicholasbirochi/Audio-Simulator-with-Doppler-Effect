package pbl;

public class Fonte
{
    // Atributos:
    private double potencia;
    private double frequencia;
    private TimbreInterface timbre;

    // Construtor:
    public Fonte(double potencia, double frequencia, TimbreInterface timbre)
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
    
    public TimbreInterface getTimbre(){
        return timbre;
    }
}
