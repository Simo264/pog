public class TextCell
{
    private String rawString;

    TextCell(Object value)
    {
        rawString = null;
        if(value != null)
            rawString = value.toString();
    }

    public boolean isValid() { return rawString != null; }
    public String getRawString() { return rawString; }
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
