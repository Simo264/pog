import java.awt.*;

public class Coordinate
{
    private Character colID;
    private Integer rowID;

    Coordinate(String coordinate)
    {
        colID = coordinate.charAt(0);
        colID = Character.toUpperCase(colID);
        rowID = Integer.parseInt(coordinate.substring(1));
    }

    Coordinate(Point point)
    {
        colID = (char) (point.x + 'A');
        rowID = point.y;
    }

    @Override
    public String toString(){ return String.valueOf(colID) + String.valueOf(rowID); }

    public Point reverse()
    {
        int col = (int) (colID - 'A');
        int row = rowID - 1;
        return new Point(col, row);
    }
}
