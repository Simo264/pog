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

        if(coordinates == null)
            return null;

        if(coordinates.size() == 1)
            return simpleAssignment(coordinates, tableModel);

        if(coordinates.size() == 2)
            return complexAssignment(coordinates, tableModel);

        return null;
    }

    private String simpleAssignment(ArrayList<Coordinate> coordinates, TableModel tableModel)
    {
        final Coordinate c = coordinates.get(0);
        final Point p = c.reverse();
        final TextCell textCell =  new TextCell(tableModel.getValueAt(p.y, p.x+1));

        if(!textCell.isValid())
            return null;

        return textCell.getRawString();
    }
    private String complexAssignment(ArrayList<Coordinate> coordinates, TableModel tableModel)
    {
        final Coordinate first = coordinates.get(0);
        final Point firstPoint = first.reverse();
        final TextCell firstCell = new TextCell(tableModel.getValueAt(firstPoint.y, firstPoint.x+1));

        final Coordinate second = coordinates.get(1);
        final Point secondPoint = second.reverse();
        final TextCell secondCell = new TextCell(tableModel.getValueAt(secondPoint.y, secondPoint.x+1));

        final EOperators operator = getOperator();

        if(!firstCell.isValid() || !secondCell.isValid())
            return new String("Null");
        if(!firstCell.isNumeric() || !secondCell.isNumeric())
            return new String("Type error");

        Double d1 = Double.parseDouble(firstCell.getRawString());
        Double d2 = Double.parseDouble(secondCell.getRawString());

        switch (operator)
        {
            case SUM:
                return String.valueOf(d1 + d2);

            case SUBTRACTION:
                return String.valueOf(d1 - d2);

            case MULTIPLICATION:
                return String.valueOf(d1 * d2);

            case DIVISION:
                return String.valueOf(d1 / d2);
        }
        return null;
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
