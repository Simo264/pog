import java.awt.*;

/**
 * La classe Coordinate rappresenta le coordinate nella tabella (es. A0, B0, C12, E3...)
 */
public class Coordinate
{
    private Character colID;
    private Integer rowID;

    /**
     * Prende in input una stringa che rappresenta una coordinata (es. "A0", "B12", "C1"...)
     * @param coordinate
     */
    Coordinate(String coordinate)
    {
        colID = coordinate.charAt(0);
        colID = Character.toUpperCase(colID);
        rowID = Integer.parseInt(coordinate.substring(1));
    }

    /**
     * Prende in input un oggetto Point che rappresenta una coordinata cartesiana (es. (0,1), (2,2)...)
     * @param point
     */
    Coordinate(Point point)
    {
        colID = (char) (point.x + 'A');
        rowID = point.y;
    }

    @Override
    public String toString(){ return String.valueOf(colID) + String.valueOf(rowID); }

    /**
     * Converte la coordinata tabellare in una coordinata cartesiana
     * @return Point
     */
    public Point reverse()
    {
        int col = (int) (colID - 'A');
        int row = rowID ;
        return new Point(col, row);
    }
}
