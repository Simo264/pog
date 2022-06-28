import java.awt.*;

/**
 * La classe Coordinate rappresenta le coordinate nella tabella (es. A0, B0, C12, E3...)
 */
public class ApplicationCoordinate
{
  public final Character COL;
  public final Integer ROW;

  /**
   * Prende in input una stringa che rappresenta una coordinata (es. "A0", "B12", "C1"...)
   * @param coordinate
   */
  ApplicationCoordinate(String coordinate)
  {
    COL = Character.toUpperCase(coordinate.charAt(0));
    ROW = Integer.parseInt(coordinate.substring(1));
  }

  /**
   * Prende in input un oggetto Point che rappresenta una coordinata cartesiana (es. (0,1), (2,2)...)
   * @param point
   */
  ApplicationCoordinate(Point point)
  {
    COL = (char) (point.x + 'A');
    ROW = point.y;
  }

  @Override
  public String toString(){ return String.valueOf(COL) + String.valueOf(ROW); }

  /**
   * Converte la coordinata tabellare in una coordinata cartesiana
   * @return Point
   */
  public Point reverse()
  {
    int col = (int) (this.COL - 'A');
    int row = ROW;
    return new Point(col, row);
  }

  /**
   * Data una stringa s ritorna true se la stringa rappresenta una coordinata
   * valida o no.
   * @param s
   * @return true se la stringa rappresenta una coordinata valida, false altrimenti
   */
  public static boolean isValid(String s)
  {
    if(s.length() != 2) return false;
    if(s.charAt(0) < 'A' || s.charAt(0) > 'Z') return false;
    if(s.charAt(1) < '0' || s.charAt(1) > '9') return false;
    return true;
  }
}
