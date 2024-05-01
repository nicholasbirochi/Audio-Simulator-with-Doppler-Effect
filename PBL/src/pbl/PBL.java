package pbl;

public class PBL
{
    //Funções Física:
    public static double Doppler(double velObsAprox, double velFonteAprox)
    {
        double velSom = 340;
        return (velSom + velObsAprox)/(velSom-velFonteAprox);
    }
    
    public static long Fatorial (int number)
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

        int i = 1;
        while (i <= 20)
        {
            
            int j = i%4 == 1? 1 : -1;
            termo = (j) * Math.pow(angulo,i) / Fatorial(i);
            estimativa += termo;
            i += 2;
        }
        return estimativa;
    }
    
    public static void main(String[] args)
    {
        System.out.println(seno(Math.PI));
    }
}