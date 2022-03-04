import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Window extends JFrame
{
    public static Dimension WINDOW_DIMENSION;
    public static Point WINDOW_LOCATION;

    public static MenuBar menuBar;
    public static Table table;

    private String configFilePath = System.getProperty("user.dir") + "/res/window.config";
    private File configFile = new File(configFilePath);

    public Window()
    {
        InitConfig();

        menuBar = new MenuBar();
        table = new Table();
        setJMenuBar(menuBar);
        add(table);
    }

    public void start() { this.setVisible(true); }


    private void InitConfig()
    {
        String title = GetValueConfigFile(configFile, "title");
        int width = Integer.parseInt(GetValueConfigFile(configFile, "width"));
        int height = Integer.parseInt(GetValueConfigFile(configFile,"height"));
        int posX = Integer.parseInt(GetValueConfigFile(configFile, "posX"));
        int posY = Integer.parseInt(GetValueConfigFile(configFile, "posY"));

        WINDOW_DIMENSION = new Dimension(width, height);
        WINDOW_LOCATION = new Point(posX, posY);

        setPreferredSize(WINDOW_DIMENSION);
        setLocation(WINDOW_LOCATION);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }

    private String GetValueConfigFile(File configFile, String searchKey)
    {
        try
        {
            Scanner scanner = new Scanner(configFile);
            while(scanner.hasNextLine())
            {
                String str = scanner.nextLine();
                String key = str.split("=")[0];
                String value = str.split("=")[1];

                if(key.contains(searchKey))
                    return value;
            }
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }

        return new String();
    }
}
