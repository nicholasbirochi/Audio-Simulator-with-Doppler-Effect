package com.pbl.model;
import java.util.List;
import java.util.ArrayList;

public class Fisica
{

    public static double doppler(double velObsAprox, double velFonteAprox, double velSom)
    {
        return (velSom + velObsAprox) / (velSom - velFonteAprox);
    }

    public static List<Double[]> amplitudeFrequenciaDoSom(Experimento exp)
    {
        List<Double[]> listaAmplitudesFrequencias = new ArrayList<Double[]>();

        double posicaoInicialFonte = exp.getPosicaoInicialFonte();
        double posicaoInicialObservador = 0;

        double posicaoAtualFonte = posicaoInicialFonte;
        double posicaoAtualObservador = posicaoInicialObservador;

        double velocidadeFonte = exp.getVelocidadeFonte();
        double velocidadeObservador = exp.getVelocidadeObservador();
        double velocidadeSom = exp.getAmbiente().getVelocidadeSom();

        double tempoDuracao = exp.getTempoDuracao();
        
        Fonte fonte = exp.getFonte();
        double frequencia = fonte.getFrequencia();
        
        double frequenciaAmostragem = exp.getTaxaAmostragem();
        double periodoAmostragem = 1 / frequenciaAmostragem;

        
        double distancia;
        double amplitude;
        double frequenciaPercebida;
        double t = 0;

        double velocidadeRelativaAproximacaoObservador = posicaoInicialObservador < posicaoInicialFonte? velocidadeObservador: -velocidadeObservador;
        double velocidadeRelativaAproximacaoFonte = posicaoInicialObservador < posicaoInicialFonte? -velocidadeFonte: velocidadeFonte;

        while (t < tempoDuracao)
        {
            distancia = Math.pow(Math.pow(posicaoInicialFonte - (velocidadeRelativaAproximacaoObservador + velocidadeRelativaAproximacaoFonte) * t, 2) + 1, 0.5);
            amplitude = 1 / distancia;

            if(posicaoAtualObservador < posicaoAtualFonte){
                velocidadeRelativaAproximacaoObservador = velocidadeObservador;
                velocidadeRelativaAproximacaoFonte = -velocidadeFonte;
            } else{
                velocidadeRelativaAproximacaoObservador = -velocidadeObservador;
                velocidadeRelativaAproximacaoFonte = velocidadeFonte;
            }

            frequenciaPercebida = frequencia * doppler(velocidadeRelativaAproximacaoObservador, velocidadeRelativaAproximacaoFonte, velocidadeSom);

            Double[] values = {amplitude, frequenciaPercebida};
            listaAmplitudesFrequencias.add(values); 

            t += periodoAmostragem;
        }
        
        return listaAmplitudesFrequencias;
    }


}
