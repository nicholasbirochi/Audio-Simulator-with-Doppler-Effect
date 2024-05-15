package pbl;
import java.util.List;
import java.util.ArrayList;

public class Fisica
{

    public static double doppler(double velObsAprox, double velFonteAprox, double velSom)
    {
        double velSom = 340;
        return (velSom + velObsAprox) / (velSom - velFonteAprox);
    }

    public static List<Double[]> intensidadeFrequenciaDoSom(Experimento exp)
    {
        double posicaoInicialFonte = exp.getPosicaoInicialFonte();
        double posicaoInicialObservador = 0;

        double posicaoAtualFonte = posicaoInicialFonte;
        double posicaoAtualObservador = posicaoInicialObservador;

        double velocidadeFonte = exp.getVelocidadeFonte();
        double velocidadeObservador = exp.getVelocidadeObservador();
        double velocidadeSom = exp.getVelocidadeSom();

        double tempoDuracao = exp.getTempoDuracao();
        
        Fonte fonte = exp.getFonte();
        double potencia = fonte.getPotencia();
        double frequencia = fonte.getFrequencia();
        
        List<Double> listaIntensidade = new ArrayList<>();
        double frequenciaAmostragem = exp.getTaxaAmostragem;
        double periodoAmostragem = 1 / frequenciaAmostragem;

        
        double distancia;
        double intensidade;
        double frequenciaPercebida;
        double t = 0;

        double velocidadeRelativaAproximacaoObservador; = posicaoInicialObservador < posicaoInicialFonte? velocidadeObservador: -velocidadeObservador;
        double velocidadeRelativaAproximacaoFonte; = posicaoInicialObservador < posicaoInicialFonte? -velocidadeFonte: velocidadeFonte;

        while (t < tempoDuracao)
        {
            distancia = Math.pow(Math.pow(posicaoInicialFonte - (velocidadeRelativaAproximacaoObservador + velocidadeRelativaAproximacaoFonte) * t, 2) + 1, 0.5);
            intensidade = potencia / (4 * Math.PI * Math.pow(distancia, 2));

            if(posicaoAtualObservador < posicaoAtualFonte){
                velocidadeRelativaAproximacaoObservador = velocidadeObservador;
                velocidadeRelativaAproximacaoFonte = velocidadeFonte;
            } else{
                velocidadeRelativaAproximacaoObservador = -velocidadeObservador;
                velocidadeRelativaAproximacaoFonte = -velocidadeFonte;
            }

            frequenciaPercebida = frequencia * doppler(velocidadeRelativaAproximacaoObservador, velocidadeRelativaAproximacaoFonte, velocidadeSom);
            listaIntensidade.add([intensidade, frequenciaPercebida]);

            t += periodoAmostragem;
        }
        
        return listaIntensidade;
    }


}
