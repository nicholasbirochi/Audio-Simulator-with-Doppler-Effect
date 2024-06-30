package com.pbl.model;

public class TimbreAbelhaEletrica implements Timbre {
    @Override
    public double onda(double angulo){
        double amostra = 0;
        for (int n = 1; n <= 10; n++) {
            amostra += Calculo.seno(n * angulo) * Math.exp(n* angulo%3);
        }
        return amostra;
    }
    
}
