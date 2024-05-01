package pbl;

public class Fisica {
    public static double doppler(double velObsAprox, double velFonteAprox)
    {
        double velSom = 340;
        return (velSom + velObsAprox)/(velSom-velFonteAprox);
    }
    
    double static double[] frequenciasAntesDepois(double frequencia, double velObsAprox, double velFonteAprox){
        double frequenciaPercebidaAntesCruzamento = frequencia * doppler(velObsAprox, velFonteAprox);
        double frequenciaPercebidaDepoisCruzamento = frequencia * doppler(-velObsAprox, -velFonteAprox);
        return [frequenciaPercebidaAntesCruzamento, frequenciaPercebidaDepoisCruzamento];
    }

    public static List<Double> intensidadeSom(double velObsAprox, double velFonteAprox, double distanciaAntesCruzamento, double distanciaDepoisCruzamento, Fonte f){
        List<Double> listaIntensidade = new List<Double>();

        double frequenciaAmostragem = 44100;
        double periodoAmostragem = 1/frequenciaAmostragem;

        double tempoTotal = (distanciaAntesCruzamento+distanciaDepoisCruzamento)/(velObsAprox+velFonteAprox);

        double potencia = f.getPotencia();

        double distancia;
        double intensidade;

        double t = 0;

        while (t < tempoTotal){
            distancia = Math.abs(distanciaAntesCruzamento - (velObsAprox+velFonteAprox)*t);
            intensidade = potencia/(4*Math.PI*Math.Pow(distancia,2));

            listaIntensidade.add(intensidade);

            t += periodoAmostragem;
        }

        return listaIntensidade;

    }
}
