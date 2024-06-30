package com.pbl.model;

public class TimbrePiano implements Timbre {
    @Override
    public double onda(double angulo){
        if(Math.abs(angulo) >= Math.PI * 2*440)
        {
            return onda(angulo % (Math.PI * 2*440));
        }        
        double amostra = Calculo.seno(angulo) * Math.exp(-0.001*angulo);
        
        return amostra;
    }
    
}