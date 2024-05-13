package pbl;
import java.util.List;
import java.util.ArrayList;

public class Fisica
{

    public static double doppler(double velObsAprox, double velFonteAprox)
    {
        double velSom = 340;
        return (velSom + velObsAprox) / (velSom - velFonteAprox);
    }

    public static double[] frequenciasAntesDepois(double frequencia, double velObsAprox, double velFonteAprox)
    {
        double frequenciaPercebidaAntesCruzamento = frequencia * doppler(velObsAprox, velFonteAprox);
        double frequenciaPercebidaDepoisCruzamento = frequencia * doppler(-velObsAprox, -velFonteAprox);
        return new double[]{frequenciaPercebidaAntesCruzamento, frequenciaPercebidaDepoisCruzamento};
    }

    public static List<Double> intensidadeSom(double velObsAprox, double velFonteAprox, double distanciaAntesCruzamento, double distanciaDepoisCruzamento, Fonte f)
    {
        List<Double> listaIntensidade = new ArrayList<>();
        double frequenciaAmostragem = 44100;
        double periodoAmostragem = 1 / frequenciaAmostragem;
        
        double tempoTotal = (distanciaAntesCruzamento + distanciaDepoisCruzamento) / (velObsAprox + velFonteAprox);
        double potencia = f.getPotencia();
        
        double distancia;
        double intensidade;
        double t = 0;

        while (t < tempoTotal)
        {
            distancia = Math.pow(Math.pow(distanciaAntesCruzamento - (velObsAprox + velFonteAprox) * t, 2) + 1, 0.5);
            intensidade = potencia / (4 * Math.PI * Math.pow(distancia, 2));
            listaIntensidade.add(intensidade);

            t += periodoAmostragem;
        }
        return listaIntensidade;
    }
}
