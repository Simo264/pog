import java.awt.*;

/**
 * Rappresenta un cella di qualsiasi tipo nella tabella.
 * Viene salvato il dato come tipo Object.
 */
public class ApplicationCell
{
    private Object data;

    /**
     * viene passato un oggetto Object come parametro
     * @param o
     */
    ApplicationCell(Object o)
    {
        data = o;
    }

    /**
     * viene salvato l'oggetto presente in tabella in coordinata <coordinate>
     * @param coordinate
     * @param tableModel
     */
    ApplicationCell(ApplicationCoordinate coordinate, ApplicationTableModel tableModel)
    {
        Point p = coordinate.reverse();
        Object o = tableModel.getValueAt(p.y, p.x+1);
        data = o;
    }

    /**
     * @return true se il dato è diverso da null (se la cella non è vuota),
     * false altrimenti
     */
    public boolean isValid()
    {
        return data != null;
    }

    /**
     * @return true se il dato contenuto nella cella è un valore numerico,
     * false altrimenti.
     */
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

    /**
     * @return true se il dato contenuto nella cella è una formula: il primo carattere
     * è un '=', false altrimenti.
     */
    public boolean containsFormula()
    {
        return data.toString().charAt(0) == '=';
    }


    /**
     * @return l'oggetto salvato nella cella
     */
    public Object getData() { return data; }
}
