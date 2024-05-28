package com.pbl.model;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Fisica
{

    public static double doppler(double velObsAprox, double velFonteAprox, double velSom)
    {
        return (velSom + velObsAprox) / (velSom - velFonteAprox);
    }

    public static List<Double[]> amplitudeFrequenciaDoSom(Experimento exp, int frequenciaAmostragem, double tempoDuracao)
    {
        List<Double[]> listaAmplitudesFrequencias = new ArrayList<Double[]>();

        double posicaoInicialFonte = exp.getPosicaoInicialFonte();
        double distanciaLateral = exp.getDistanciaLateral();
        double posicaoInicialObservador = 0;

        double posicaoAtualFonte = posicaoInicialFonte;
        double posicaoAtualObservador = posicaoInicialObservador;

        double velocidadeFonte = exp.getVelocidadeFonte();
        double velocidadeObservador = exp.getVelocidadeObservador();
        double velocidadeSom = exp.getAmbiente().getVelocidadeSom();

        Fonte fonte = exp.getFonte();
        double frequencia = fonte.getFrequencia();

        double periodoAmostragem = 1 / frequenciaAmostragem;


        double distancia;
        double amplitude;
        double frequenciaPercebida;
        double t = 0;

        double velocidadeRelativaAproximacaoObservador = posicaoInicialObservador < posicaoInicialFonte? velocidadeObservador: -velocidadeObservador;
        double velocidadeRelativaAproximacaoFonte = posicaoInicialObservador < posicaoInicialFonte? -velocidadeFonte: velocidadeFonte;

        while (t < tempoDuracao)
        {
            distancia = Math.pow(Math.pow(posicaoInicialFonte - (velocidadeRelativaAproximacaoObservador + velocidadeRelativaAproximacaoFonte) * t, 2) + Math.pow(distanciaLateral, 2), 0.5);
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
    
    public static void ouvirTimbre(Timbre timbre, String caminhoArquivo, int taxaAmostragem, double duracao) throws IOException, UnsupportedAudioFileException{
        Ambiente ambiente = new Ambiente("ar", 330);
        Fonte fonte = new Fonte("fonte qualquer", 5, 440, timbre);
        Experimento exp = new Experimento("Experimento de teste", 0, 1, 0, 0, ambiente, fonte);
        
        exp.criarArquivoDeSimulacao(caminhoArquivo, int taxaAmostragem, double duracao);
    }


}