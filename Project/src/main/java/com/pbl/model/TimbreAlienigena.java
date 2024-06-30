package com.pbl.model;

public class TimbreAlienigena implements Timbre {
    @Override
    public double onda(double angulo){
        double amostra = 0;
        for (int n = 1; n <= 10; n++) {
            amostra += Calculo.seno(n * angulo) * Math.exp(2 * Math.PI*-0.001 * n* angulo%(2 * Math.PI));
        }
        return amostra;
    }
    
}
