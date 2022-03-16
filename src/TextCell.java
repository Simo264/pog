/**
 * Rappresenta una cella nella tabella
 */
public class TextCell
{
    private String rawString;

    TextCell(Object value)
    {
        rawString = null;
        if(value != null)
            rawString = value.toString();
    }

    /**
     * @return VERO se il contenuto della cella non è null, FALSO altrimenti
     */
    public boolean isValid() { return rawString != null; }

    /**
     * @return il contenuto della cella
     */
    public String getRawString() { return rawString; }

    /**
     * @return VERO se il contenuto della cella è un valore numerico, FALSO altrimenti
     */
    public boolean isNumeric()
    {
        try
        {
            Double d = Double.parseDouble(rawString);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }
}
