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

        // caso 1: entrambi gli operandi rappresentano delle coordinate
        // Esempio: =A0+A1, =B0+A1 ...
        if(
                ApplicationCoordinate.isValid(operands[0]) &&
                ApplicationCoordinate.isValid(operands[1]))
        {
            ApplicationCell c1 = new ApplicationCell(
                    new ApplicationCoordinate(operands[0]), tableModel);

            ApplicationCell c2 = new ApplicationCell(
                    new ApplicationCoordinate(operands[1]), tableModel);

            // se entrambe le celle contengono valori numerici
            // ritorno la somma dei due valori
            if(c1.isNumeric() && c2.isNumeric())
                return ApplicationUtilities.sum(
                        Double.parseDouble(c1.getData().toString()),
                        Double.parseDouble(c2.getData().toString()));

            // se una delle due celle contiene un valore non numerico
            // ritorno la concatenazione testuale delle due
            return ApplicationUtilities.sum(c1.getData().toString(), c2.getData().toString());
        }

        // caso 2: se entrambi gli operandi non sono coordinate
        // ma sono valori numerici/testuali
        // Esempio: =12+3, =3+21 ...
        else if (
                !ApplicationCoordinate.isValid(operands[0]) &&
                !ApplicationCoordinate.isValid(operands[1]))
        {
            // se entrambi sono valori numerici li sommo
            if(
                    ApplicationUtilities.stringNumeric(operands[0]) &&
                    ApplicationUtilities.stringNumeric(operands[1]))
                return ApplicationUtilities.sum(
                        Double.parseDouble(operands[0]),
                        Double.parseDouble(operands[1]));

            // altrimenti li concateno
            return ApplicationUtilities.sum(operands[0], operands[1]);
        }

        // caso 3: sono uno dei due operandi rappresenta una coordinata
        // mentra l'altro rappresenta una valore numerico/testuale
        // Esempio: =A0+2, =32+B1, =hello+B3 ...
        else
        {
            int i = 0; // indice operando coordinata
            int j = 1; // indice operando valore numerico/testuale

            // se il secondo operando è una coordinata
            if(ApplicationCoordinate.isValid(operands[1]))
            {
                i = 1;
                j = 0;
            }

            ApplicationCell cell = new ApplicationCell(
                    new ApplicationCoordinate(operands[i]), tableModel);

            // se la cella contiene un valore numerico
            // e il secondo operando contiene un valore numerico
            // ritorno la somma dei due valori.
            if(cell.isNumeric() && ApplicationUtilities.stringNumeric(operands[j]))
                return ApplicationUtilities.sum(
                        Double.parseDouble(cell.getData().toString()),
                        Double.parseDouble(operands[j]));

            // Atrimenti ritorno la concatenazione testuale
            return ApplicationUtilities.sum(cell.getData().toString(), operands[j]);
        }
    }
    private Object resolveSub(ApplicationTableModel tableModel)
    {
        String[] operands = formula.split("-");

        // caso 1: entrambi gli operandi rappresentano delle coordinate
        // Esempio: =A0+A1, =B0+A1 ...
        if(
                ApplicationCoordinate.isValid(operands[0]) &&
                ApplicationCoordinate.isValid(operands[1]))
        {
            ApplicationCell c1 = new ApplicationCell(
                    new ApplicationCoordinate(operands[0]), tableModel);

            ApplicationCell c2 = new ApplicationCell(
                    new ApplicationCoordinate(operands[1]), tableModel);

            // se entrambe le celle contengono valori numerici
            // ritorno la differenza dei due valori
            if(c1.isNumeric() && c2.isNumeric())
                return ApplicationUtilities.sub(
                        Double.parseDouble(c1.getData().toString()),
                        Double.parseDouble(c2.getData().toString())
                );

            // altrimenti return null: non posso sottrarre delle stringhe
            return null;
        }

        // caso 2: se entrambi gli operandi non sono coordinate
        // ma sono valori numerici
        // Esempio: =12-3, =3-21 ...
        else if (
                !ApplicationCoordinate.isValid(operands[0]) &&
                        !ApplicationCoordinate.isValid(operands[1]))
        {
            // se entrambi sono valori numerici li sommo
            if(
                    ApplicationUtilities.stringNumeric(operands[0]) &&
                    ApplicationUtilities.stringNumeric(operands[1]))
                return ApplicationUtilities.sub(
                        Double.parseDouble(operands[0]),
                        Double.parseDouble(operands[1])
                );

            // altrimenti return null: non posso sottrarre delle stringhe
            return null;
        }

        // caso 3: sono uno dei due operandi rappresenta una coordinata
        // mentra l'altro rappresenta una valore numerico
        else
        {
            int i = 0; // indice operando coordinata
            int j = 1; // indice operando non coordinata

            // se il secondo operando è una coordinata
            if(ApplicationCoordinate.isValid(operands[1]))
            {
                i = 1;
                j = 0;
            }

            ApplicationCell cell = new ApplicationCell(
                    new ApplicationCoordinate(operands[i]), tableModel);

            // se la cella contiene un valore numerico
            // e il secondo operando contiene un valore numerico
            // ritorno la somma dei due valori.
            if(cell.isNumeric() && ApplicationUtilities.stringNumeric(operands[j]))
            {
                // Esempio: =A0-4, =C0-12.4
                if(i == 0)
                    return ApplicationUtilities.sub(
                            Double.parseDouble(cell.getData().toString()),
                            Double.parseDouble(operands[j]));

                // Esempio: =21-B2, =2-C0
                else
                    return ApplicationUtilities.sub(
                            Double.parseDouble(operands[j]),
                            Double.parseDouble(cell.getData().toString()));
            }
            return null;
        }
    }

    private EOperations operation()
    {
        if(formula.contains("+")) return EOperations.SUM;
        if(formula.contains("-")) return EOperations.SUB;
        return EOperations.ASSIGNMENT;
    }
}

