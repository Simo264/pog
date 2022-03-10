public class NumericCell extends SimpleTableCell
{
    private Double value;

    NumericCell(Object obj)
    {
        super(obj);
        value = Double.parseDouble(getRawValue());
    }

    public Double getNumericValue() { return value; }
}