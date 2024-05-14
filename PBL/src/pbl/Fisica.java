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

    public static double[] frequenciasAntesDepois(Experimento exp)
    {
        Fonte fonte = ext.getFonte();
        double frequencia = fonte.getFrequencia();
        double posicaoInicialFonte = exp.getPosicaoInicialFonte();
        double posicaoInicialObservador = 0;
        double velocidadeFonte = getVelocidadeFonte();
        double velocidadeObservador = getVelocidadeObservador();

        double velocidadeRelativaAproximacaoObservador = posicaoInicialObservador < posicaoInicialFonte? velocidadeObservador: -velocidadeObservador;
        double velocidadeRelativaAproximacaoFonte = posicaoInicialObservador < posicaoInicialFonte? -velocidadeFonte: velocidadeFonte;

        double frequenciaPercebidaAntesCruzamento = frequencia * doppler(velocidadeRelativaAproximacaoObservador, velocidadeRelativaAproximacaoFonte);
        double frequenciaPercebidaDepoisCruzamento = frequencia * doppler(-velocidadeRelativaAproximacaoObservador, -velocidadeRelativaAproximacaoFonte);
        return new double[]{frequenciaPercebidaAntesCruzamento, frequenciaPercebidaDepoisCruzamento};
    }

    public static List<Double> intensidadeSom(Experimento exp)
    {
        double posicaoInicialFonte = exp.getPosicaoInicialFonte();
        double velocidadeFonte = exp.getVelocidadeFonte();
        double velocidadeObservador = exp.getVelocidadeObservador();
        double velocidadeSom = exp.getVelocidadeSom();
        double tempoDuracao = exp.getTempoDuracao();
        
        Fonte fonte = exp.getFonte();
        double potencia = fonte.getPotencia();
        
        List<Double> listaIntensidade = new ArrayList<>();
        double frequenciaAmostragem = 44100;
        double periodoAmostragem = 1 / frequenciaAmostragem;

        
        double distancia;
        double intensidade;
        double t = 0;

        double velocidadeRelativaAproximacaoObservador = posicaoInicialObservador < posicaoInicialFonte? velocidadeObservador: -velocidadeObservador;
        double velocidadeRelativaAproximacaoFonte = posicaoInicialObservador < posicaoInicialFonte? -velocidadeFonte: velocidadeFonte;

        while (t < tempoDuracao)
        {
            distancia = Math.pow(Math.pow(posicaoInicialFonte - (velocidadeRelativaAproximacaoObservador + velocidadeRelativaAproximacaoFonte) * t, 2) + 1, 0.5);
            intensidade = potencia / (4 * Math.PI * Math.pow(distancia, 2));
            listaIntensidade.add(intensidade);

            t += periodoAmostragem;
        }
        
        return listaIntensidade;
    }
}
