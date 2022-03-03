import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Window extends JFrame
{
    private String configFilePath = System.getProperty("user.dir") + "/res/window.init";
    private File configFile = new File(configFilePath);


    String[] columnNames = {
            "#",
            "First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian"
    };

    Object[][] data = {
            {1,"Kathy", "Smith", "Snowboarding", 5, false},
            {2,"John", "Doe", "Rowing", 3, true},
            {3,"Sue", "Black", "Knitting", 2, false},
            {4,"Jane", "White", "Speed reading", 20, true},
            {5,"Joe", "Brown", "Pool", 10, false},
            {6,"Kathy", "Smith", "Snowboarding", 5, false},
            {7,"John", "Doe", "Rowing", 3, true},
            {8,"Sue", "Black", "Knitting", 2, false},
            {9,"Jane", "White", "Speed reading", 20, true},
            {10,"Joe", "Brown", "Pool", 10, false},
            {11,"Kathy", "Smith", "Snowboarding", 5, false},
            {12,"John", "Doe", "Rowing", 3, true},
            {13,"Sue", "Black", "Knitting", 2, false},
            {14,"Jane", "White", "Speed reading", 20, true},
            {15,"Joe", "Brown", "Pool", 10, false},
            {16,"Kathy", "Smith", "Snowboarding", 5, false},

    };


    public Window()
    {
        InitConfig();
        add(new MenuBar());

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 50));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
    public void start() { this.setVisible(true); }


    private void InitConfig()
    {
        String title = GetValueConfigFile(configFile, "title");
        int width = Integer.parseInt(GetValueConfigFile(configFile, "width"));
        int height = Integer.parseInt(GetValueConfigFile(configFile,"height"));
        int posX = Integer.parseInt(GetValueConfigFile(configFile, "posX"));
        int posY = Integer.parseInt(GetValueConfigFile(configFile, "posY"));

        setPreferredSize(new Dimension(width, height));
        setLocation(new Point(posX, posY));
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
