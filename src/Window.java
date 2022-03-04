import javax.swing.*;
import java.awt.*;

public class Window extends JFrame
{
    private WindowConfig config;

    public static Dimension WINDOW_DIMENSION;
    public static Point WINDOW_LOCATION;

    private MenuBar menuBar;
    private Table table;

    public Window()
    {
        config = new WindowConfig(this);
        WINDOW_DIMENSION = getPreferredSize();
        WINDOW_LOCATION = getLocation();

        menuBar = new MenuBar(this);
        table = new Table(this);
        setJMenuBar(menuBar);
        add(table);
    }

    public MenuBar getMenu() { return menuBar; }
    public Table getTable() { return table; }

    public void start() { this.setVisible(true); }
}
