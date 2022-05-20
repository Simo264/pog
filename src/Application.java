import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

/**
 * La classe Application estende JFrame, rappresenta quindi la finestra principale in cui si aggiungeranno i vari
 * componenti quali MenuBarComponent e il TablePanel.
 *
 * Dato il file di configurazione "window.config" che si trova nella cartella configs/, vengono impostate le
 * proprietà del JFrame (es. titolo, dimensione, posizione...)
 */
public class Application extends JFrame
{

    /***** Window components ******/
    /******************************/
    public ApplicationMenuBar applicationMenuBar;
    public ApplicationPanel applicationPanel;


    /***** Window properties ******/
    /******************************/
    public Workspace workspace;
    public LinkedHashMap<String, String> windowProperties;



    /***** Constructor ******/
    /******************************/
    public Application()
    {
        loadProperties();
    }

    public void start()
    {
        workspace = new Workspace();

        setFrameProperties();
        addMenuBar();

        addPanel();
    }




    private void loadProperties()
    {
        try
        {
            File configurationFile = new ConfigurationFileWindow().getConfigurationFile();
            FileParser fileParser = new FileParser(configurationFile);
            windowProperties = fileParser.getProperties();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }
    private void setFrameProperties()
    {
        String title = windowProperties.get( "title");
        int width = Integer.parseInt(windowProperties.get( "width"));
        int height = Integer.parseInt(windowProperties.get("height"));
        int posX = Integer.parseInt(windowProperties.get( "posX"));
        int posY = Integer.parseInt(windowProperties.get( "posY"));
        boolean resizable = Boolean.parseBoolean(windowProperties.get( "resizable"));

        Dimension dimension = new Dimension(width, height);
        Point location = new Point(posX, posY);

        setTitle(title);
        setPreferredSize(dimension);
        setLocation(location);
        setResizable(resizable);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    private void addMenuBar()
    {
        applicationMenuBar = new ApplicationMenuBar(this);
        setJMenuBar(applicationMenuBar);
    }
    private void addPanel()
    {
        applicationPanel = new ApplicationPanel(this);
        add(applicationPanel);
    }
}
