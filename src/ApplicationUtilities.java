/**
 * Contiene delle funzioni per il calcolo della somma, sottrazione
 * e una funzione per il controllo di una stringa numerica; utili per
 * la risoluzione delle formule.
 */
public class ApplicationUtilities
{
    public static String sum(String s1, String s2)
    {
        return s1 + s2;
    }
    public static Double sum(Double d1, Double d2)
    {
        return d1 + d2;
    }
    public static Double sub(Double d1, Double d2)
    {
        return d1 -d2;
    }
    public static boolean stringNumeric(String s)
    {
        try
        {
            Double.parseDouble(s);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

}
