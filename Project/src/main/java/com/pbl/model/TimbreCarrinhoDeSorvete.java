
package com.pbl.model;

public class TimbreCarrinhoDeSorvete implements Timbre{
    @Override
    public double onda(double angulo){
        double amostra = 0;
        for (int n = 1; n <= 10; n++) {
            amostra += (1.0 / n) * Calculo.seno(n * angulo) * Math.exp((-0.001 * (n%5) * angulo)%5);
        }
        return amostra;
    }
}
