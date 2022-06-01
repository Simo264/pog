import java.awt.*;
import java.util.ArrayList;

/**
 * La classe Formula rappresenta una semplice formula in forma testuale (es. "=A0+B0").
 * Una formula per essere valida deve presentare delle coordinate tabellari e non valori numerici.
 */
public class Formula
{
    private final String delimiters = "-|\\+|\\*|\\/";
    private String rawFormula;

    Formula(String formula)
    {
        rawFormula = formula.substring(1);
    }

    /**
     * Il metodo resolve() può operare in due modi:
     * 1. la formula è un'assegnazione (es. "=A0", "=B2"): in questo caso la cella selezionata assumerà il valore
     * della cella nella posizione data
     *
     * 2. la formula è una equazione (es. "=A1+B2"): in questo caso la cella selezionata assumerà il valore
     * in posizione A1 + B2. Se in A1 o in B2 non saranno presenti valori numerici o sono presenti valori nulli
     * non verrà fatta nessuna operazione.
     *
     * @param applicationTableModel
     * @return result
     */
    public String resolve(ApplicationTableModel applicationTableModel)
    {
        final ArrayList<ApplicationCoordinate> applicationCoordinates = splitCoordinates();

        if(applicationCoordinates == null)
            return null;

        if(applicationCoordinates.size() == 1)
            return simpleAssignment(applicationCoordinates, applicationTableModel);

        if(applicationCoordinates.size() == 2)
            return complexAssignment(applicationCoordinates, applicationTableModel);

        return null;
    }

    private String simpleAssignment(ArrayList<ApplicationCoordinate> applicationCoordinates, ApplicationTableModel applicationTableModel)
    {
        final ApplicationCoordinate c = applicationCoordinates.get(0);
        final Point p = c.reverse();
        final TextCell textCell =  new TextCell(applicationTableModel.getValueAt(p.y, p.x+1));

        if(!textCell.isValid())
            return null;

        return textCell.getRawString();
    }
    private String complexAssignment(ArrayList<ApplicationCoordinate> applicationCoordinates, ApplicationTableModel applicationTableModel)
    {
        final ApplicationCoordinate first = applicationCoordinates.get(0);
        final Point firstPoint = first.reverse();
        final TextCell firstCell = new TextCell(applicationTableModel.getValueAt(firstPoint.y, firstPoint.x+1));

        final ApplicationCoordinate second = applicationCoordinates.get(1);
        final Point secondPoint = second.reverse();
        final TextCell secondCell = new TextCell(applicationTableModel.getValueAt(secondPoint.y, secondPoint.x+1));

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
    private ArrayList<ApplicationCoordinate> splitCoordinates()
    {
        ArrayList<ApplicationCoordinate> applicationCoordinates = new ArrayList<>();
        try
        {
            for (String s : rawFormula.split(delimiters))
                applicationCoordinates.add(new ApplicationCoordinate(s));
        }
        catch (StringIndexOutOfBoundsException e)
        {
            return null;
        }
        return applicationCoordinates;
    }
    private EOperators getOperator()
    {
        if(rawFormula.contains("-")) return EOperators.SUBTRACTION;
        if(rawFormula.contains("+")) return EOperators.SUM;
        if(rawFormula.contains("*")) return EOperators.MULTIPLICATION;
        if(rawFormula.contains("/")) return EOperators.DIVISION;
        return EOperators.NONE;
    }


    private enum EOperators
    {
        SUM,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION,
        NONE
    }
}
