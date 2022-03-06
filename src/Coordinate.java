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

    public char getColID() { return colID; }
    public int getrowID() { return rowID; }
    public String toString(){ return String.valueOf(colID) + String.valueOf(rowID); }

    // From Coordinate to Point
    public Point reverse()
    {
        int x = (int) (colID - 'A');
        return new Point(x, rowID);
    }
}
