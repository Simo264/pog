import java.awt.*;

public class Coordinate
{
    private char colID;
    private int rowID;

    Coordinate(String coordinate)
    {
        colID = coordinate.charAt(0);
        rowID = Integer.parseInt(coordinate.substring(1));
    }

    Coordinate(Point point)
    {
        colID = (char) (point.x + 'A');
        rowID = point.y;
    }

    @Override
    public String toString(){ return String.valueOf(colID) + String.valueOf(rowID + 1); }

    // From Coordinate to Point
    public Point reverse()
    {
        int x = (int) (colID - 'A');
        return new Point(x, rowID);
    }
}
