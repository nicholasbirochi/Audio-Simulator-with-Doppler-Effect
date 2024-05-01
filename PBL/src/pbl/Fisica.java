package pbl;

public class Fisica {
    public static double doppler(double velObsAprox, double velFonteAprox)
    {
        double velSom = 340;
        return (velSom + velObsAprox)/(velSom-velFonteAprox);
    }

    public static List<Double[]> geraSom(double velObsAprox, double velFonteAprox, double distanciaAntesCruzamento, double distanciaDepoisCruzamento, double frequenciaSomFonte, double potenciaSomFonte){
        
    }
}
