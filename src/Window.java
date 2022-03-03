import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Window extends JFrame
{
    public Window()
    {
        InitConfig();

        //this.setTitle(title);
        //this.setPreferredSize(dimension);
        //this.setLocation(point);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //AddMenuBar();
        //this.pack();
    }

    private void InitConfig()
    {
        String configFilePath = System.getProperty("user.dir") + "/res/window.init";
        File configFile = new File(configFilePath);

        String title = GetValueConfigFile(configFile, "title");
        int width = Integer.parseInt(GetValueConfigFile(configFile, "width"));
        int height = Integer.parseInt(GetValueConfigFile(configFile,"height"));
        int posX = Integer.parseInt(GetValueConfigFile(configFile, "posX"));
        int posY = Integer.parseInt(GetValueConfigFile(configFile, "posY"));
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



    public void Show()
    {
        this.setVisible(true);
    }

    private void AddMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu file = new JMenu("file");
        menuBar.add(file);

        JMenu options = new JMenu("options");
        menuBar.add(options);

        JMenu help = new JMenu("help");
        menuBar.add(help);
    }

}
