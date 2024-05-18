
package pbl;


public class TimbrePuro implements TimbreInterface {
    @Override
    public double onda(double angulo){
        
        return Calculo.seno(angulo);
        
    }
}