package com.pbl.model;

import java.util.List;

public class Calculo
{
    // Função para o cálculo do fatorial, para não utilizar da biblioteca:
    public static long fatorial (int number)
    {
        long value = 1;
        for(int i = 1; i <= number; i++)
        {
            value *= i;
        }
        return value;
    }

    // Função para o cálculo do seno, para não utilizar da biblioteca:
    public static double seno(double angulo)
    {
        // Normalizando o ângulo para o intervalo [-2π, 2π]:
        if(Math.abs(angulo) >= Math.PI * 2)
        {
            return seno(angulo % (Math.PI * 2));
        }
        double estimativa = 0; // Inicializando a estimativa do seno...
        double termo; // Variável para armazenar o termo atual da série de Taylor...
    
        // Loop para calcular a série de Taylor do seno:
        for (int i = 1; i < 20;) // Utilizando 20 termos da série...
        {
            int j = i%4 == 1? 1 : -1; // Alternando o sinal do termo a cada dois termos...
            termo = (j) * Math.pow(angulo,i) / fatorial(i); // Calculando o termo atual...
            estimativa += termo; // Adicionando o termo à estimativa do seno...
            i += 2; // Incrementando o contador por 2 para passar para o próximo termo ímpar...
        }
        return estimativa; // Retornando a estimativa do seno...
    }

    
    public static double acharMax(List<Double[]> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("Lista está vazia ou nula");
        }

        double max = Double.MIN_VALUE;
        for (Double[] num : numbers) {
            if (num[0] > max) {
                max = num[0];
            }
        }
        return max;
    }
}
