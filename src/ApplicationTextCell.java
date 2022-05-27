public class ApplicationTextCell
{
    private String data = null;
    ApplicationTextCell(Object object)
    {
        data = (String) object;
    }

    public boolean isNumeric()
    {
        if(data == null) return false;
        try
        {
            Double.parseDouble(data);
        }
        catch (NumberFormatException e)
        {
            return false;
        }

        return true;
    }
    public String getData()
    {
        return data;
    }
}
