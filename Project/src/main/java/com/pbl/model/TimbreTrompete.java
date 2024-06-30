
package com.pbl.model;

public class TimbreTrompete implements Timbre {
    @Override
    public double onda(double angulo){
        double amostra = 0;
        for (int n = 1; n <= 10; n++) {
            amostra += (1.0 / n) * Calculo.seno(n * angulo);
        }
        return amostra;
    }
    
}
