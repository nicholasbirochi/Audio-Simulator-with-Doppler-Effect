package pbl;

public class Fonte
{
    // Atributos:
    private double potencia;
    private double frequencia;
    private TimbreInterface timbre;
    private String nome;
    private int id;

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

    public String getNome(){
        return this.nome;
    }
    
    public int getId(){
        return this.id;
    }
}
