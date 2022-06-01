import java.awt.*;

public class ApplicationCell
{
    private Object data;

    ApplicationCell(Object o)
    {
        data = o;
    }
    ApplicationCell(ApplicationCoordinate coordinate, ApplicationTableModel tableModel)
    {
        Point p = coordinate.reverse();
        Object o = tableModel.getValueAt(p.y, p.x+1);
        data = o;
    }
    ApplicationCell(int row, int col, ApplicationTableModel tableModel)
    {
        Object o = tableModel.getValueAt(row, col);
        data = o;
    }

    public boolean isValid()
    {
        return data != null;
    }
    public boolean isNumeric()
    {
        if(data == null) return false;
        try
        {
            Double.parseDouble(data.toString());
        }
        catch (NumberFormatException e)
        {
            return false;
        }

        return true;
    }
    public boolean containsFormula()
    {
        return data.toString().charAt(0) == '=';
    }
    public boolean containsText()
    {
        return !isNumeric() && !containsFormula();
    }

    public Object getData() { return data; }
}
