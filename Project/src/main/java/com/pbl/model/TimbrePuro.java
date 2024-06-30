
package com.pbl.model;


public class TimbrePuro implements Timbre {
    @Override
    public double onda(double angulo){
        
        return Calculo.seno(angulo);
        
    }
}