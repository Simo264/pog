public class SimpleTableCell
{
    private Object rawObject;

    SimpleTableCell(Object obj) { rawObject = obj; }

    public boolean isValid() { return rawObject != null; };
    public String getRawValue() { return rawObject.toString(); }
    public Object getRawObject() { return rawObject; }

    public ETableCellTypes classifyValue()
    {
        if(containsFormula()) return ETableCellTypes.FORMULA;
        if(isNumeric()) return ETableCellTypes.NUMERIC;
        return ETableCellTypes.TEXT;
    }

    private boolean isNumeric()
    {
        try
        {
            double d = Double.parseDouble(getRawValue());
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    private boolean containsFormula()
    {
        return getRawValue().charAt(0) == '=';
    }
}
