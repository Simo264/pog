import java.awt.*;

public class ApplicationCellFormula extends ApplicationCell
{
    private String formula = null;

    private enum EOperations { SUM, SUB, ASSIGNMENT }


    ApplicationCellFormula(Object o)
    {
        super(o);
        formula = o.toString().substring(1);
    }

    ApplicationCellFormula(ApplicationCell cell)
    {
        super(cell.getData());
        formula = cell.getData().toString().substring(1);
    }

    @Override
    public boolean containsFormula() { return true; }

    @Override
    public boolean containsText() { return false; }

    @Override
    public boolean isValid()
    {
        if(formula.isEmpty())
            return false;

        String[] strSplit = formula.split("-|\\+");
        if((operation() == EOperations.SUB || operation() == EOperations.SUM)
            && strSplit.length == 1)
            return false;

        return true;
    }

    @Override
    public Object getData()
    {
        return formula;
    }

    public Object resolve(ApplicationTableModel tableModel)
    {
        var op = operation();
        switch (op)
        {
            case SUB ->
            {
                return resolveSub(tableModel);
            }
            case SUM ->
            {
                return resolveSum(tableModel);
            }
            case ASSIGNMENT ->
            {
                return resolveAssignment(tableModel);
            }
        }
        return null;
    }



    private Object resolveAssignment(ApplicationTableModel tableModel)
    {
        if(!ApplicationCoordinate.isValid(formula))
            return null;


        ApplicationCoordinate coordinate = new ApplicationCoordinate(formula);
        Point p = coordinate.reverse();
        Object o = tableModel.getValueAt(p.y, p.x + 1);
        return o;
    }
    private Object resolveSum(ApplicationTableModel tableModel)
    {
        String[] operands = formula.split("\\+");
        if(bothCoordinates(operands))
        {
            ApplicationCoordinate c1 = new ApplicationCoordinate(operands[0]);
            Point p1 = c1.reverse();
            Object o1 = tableModel.getValueAt(p1.y, p1.x+1);
            ApplicationCell cell1 = new ApplicationCell(o1);

            ApplicationCoordinate c2 = new ApplicationCoordinate(operands[1]);
            Point p2 = c2.reverse();
            Object o2 = tableModel.getValueAt(p2.y, p2.x+1);
            ApplicationCell cell2 = new ApplicationCell(o2);

            if(cell1.isNumeric() && cell2.isNumeric())
                return Double.parseDouble(o1.toString()) + Double.parseDouble(o2.toString());

            else
                return o1.toString() + o2.toString();

        }
        else
        {

        }

        return null;
    }
    private Object resolveSub(ApplicationTableModel tableModel)
    {
        return null;
    }

    private boolean bothCoordinates(String[] operands)
    {
        return ApplicationCoordinate.isValid(operands[0]) &&
                ApplicationCoordinate.isValid(operands[1]);
    }

    private EOperations operation()
    {
        if(formula.contains("+")) return EOperations.SUM;
        if(formula.contains("-")) return EOperations.SUB;
        return EOperations.ASSIGNMENT;
    }
}

