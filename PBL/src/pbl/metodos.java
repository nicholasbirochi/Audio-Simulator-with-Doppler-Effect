package pbl;

public class Fisica {
    public static double doppler(double velObsAprox, double velFonteAprox)
    {
        double velSom = 340;
        return (velSom + velObsAprox)/(velSom-velFonteAprox);
    }

    public static long fatorial (int number)
    {
        long value = 1;
        for(int i = 1; i <= number; i++)
        {
            value *= i;
        }
        return value;
    }

    public static double seno(double angulo)
    {

        if(Math.abs(angulo) >= Math.PI * 2)
        {
            return seno(angulo % (Math.PI * 2));
        }


        double estimativa = 0;
        double termo;

        for (int i = 1; i < 20;)
        {

            int j = i%4 == 1? 1 : -1;
            termo = (j) * Math.pow(angulo,i) / fatorial(i);
            estimativa += termo;
            i += 2;
        }
        return estimativa;
    }
}
