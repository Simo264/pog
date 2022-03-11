import java.awt.*;
import java.util.ArrayList;

public class Formula
{
    private final String delimiters = "-|\\+|\\*|\\/";
    private String rawFormula;

    Formula(String formula)
    {
        rawFormula = formula.substring(1);
    }

    public String resolve(TableModel tableModel)
    {
        final ArrayList<Coordinate> coordinates = splitCoordinates();

        if(coordinates == null || coordinates.size() == 0)
            return null;

        if(coordinates.size() == 1)
        {
            final Coordinate c = coordinates.get(0);
            final Point p = c.reverse();
            final Object o = tableModel.getValueAt(p.x, p.y);

            System.out.println("c=" + c.toString() + " p=" + p.toString() + " o=" + o);

            if(o == null)
                return null;
            return o.toString();
        }


        /*
        final Coordinate c1 = getC1();
        final Point p1 = c1.reverse();
        final Coordinate c2 = getC2();
        final Point p2 = c2.reverse();
        final EOperators operator = getOperator();


        final TextCell cell1 = new TextCell(tableModel.getValueAt(p1.x, p1.y));
        final TextCell cell2 = new TextCell(tableModel.getValueAt(p2.x, p2.y));

        System.out.println(c1 + " " + c2);

        switch (operator)
        {
            case SUM:
            {
                break;
            }

            case SUBTRACTION:
            {
                break;
            }

            case MULTIPLICATION:
            {
                break;
            }

            case DIVISION:
            {
                break;
            }

            case NONE:
            {


                break;
            }
        }
        if(!cell1.isValid() || !cell2.isValid()) return new String("*Error*");

        if(!cell1.isNumeric() || !cell2.isNumeric())
        {
            return cell1.getRawString() + cell2.getRawString();
        }
        if(cell1.isNumeric() && cell2.isNumeric())
        {

        }
        */
        return new String("*Error*");
    }


    private ArrayList<Coordinate> splitCoordinates()
    {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        try
        {
            for (String s : rawFormula.split(delimiters))
                coordinates.add(new Coordinate(s));
        }
        catch (StringIndexOutOfBoundsException e)
        {
            return null;
        }
        return coordinates;
    }
    private EOperators getOperator()
    {
        if(rawFormula.contains("-")) return EOperators.SUBTRACTION;
        if(rawFormula.contains("+")) return EOperators.SUM;
        if(rawFormula.contains("*")) return EOperators.MULTIPLICATION;
        if(rawFormula.contains("/")) return EOperators.DIVISION;
        return EOperators.NONE;
    }



    public enum EOperators
    {
        SUM,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION,
        NONE
    }
}
