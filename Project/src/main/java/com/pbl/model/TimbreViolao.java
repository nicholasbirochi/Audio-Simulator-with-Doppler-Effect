package com.pbl.model;

public class TimbreViolao implements Timbre {
    @Override
    public double onda(double angulo){

        if(Math.abs(angulo) > Math.PI * 2 * 440)
        {
            return onda(angulo % (Math.PI * 2*440));
        }
        double amostra = 0;
        for (int n = 1; n <= 10; n++) {
            amostra += (1.0 / n) * Calculo.seno(n * angulo) * Math.exp(-0.001 * n* angulo);
        }
        return amostra;
    }
    
}
