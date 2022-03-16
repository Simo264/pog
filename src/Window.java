import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

/**
 * La classe Window estende JFrame, rappresenta quindi la finestra principale in cui si aggiungeranno i vari
 * componenti quali MenuBarComponent e il TablePanel.
 *
 * Dato il file di configurazione "window.init.config" che si trova nella cartella configs/, vengono impostate le
 * proprietà del JFrame (es. titolo, dimensione, posizione...)
 */
public class Window extends JFrame
{
    private Workspace workspace;
    private File windowConfigFile;
    private LinkedHashMap<String, String> windowProperties;

    private MenuBarComponent menuBarComponent;
    private TablePanel tablePanel;

    public Window()
    {
        workspace = new Workspace();

        initConfigFile();
        loadProperties();
        setWindowProperties();
        addWindowComponent();
    }

    private void initConfigFile()
    {
        try
        {
            windowConfigFile = new ConfigurationFileWindow().getConfigurationFile();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }
    private void loadProperties()
    {
        FileParser fileParser = new FileParser(windowConfigFile);
        windowProperties = fileParser.getProperties();
    }
    private void setWindowProperties()
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
        setVisible(false);
        setResizable(resizable);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
    private void addWindowComponent()
    {
        menuBarComponent = new MenuBarComponent(this);
        tablePanel = new TablePanel(this);
        setJMenuBar(menuBarComponent);
        add(tablePanel);
    }

    /**
     * Il metodo start() di Window rende visible il frame
     */
    public void start() { this.setVisible(true); }

    /**
     * Ritorna il componente MenuBarComponent
     * @return menuBarComponent
     */
    public MenuBarComponent getMenuBarComponent() { return menuBarComponent; }

    /**
     * Ritorna il componente TablePanel
     * @return tablePanel
     */
    public TablePanel getTablePanel() { return tablePanel; }

    /**
     * Ritorna l'oggetto workspace
     * @return workspace
     */
    public Workspace getWorkspace() { return workspace; }

    /**
     * Ritorna le proprietà del frame nel formato LinkedHashMap
     * @return workspace
     */
    public LinkedHashMap<String, String> getWindowProperties() { return windowProperties; }

    /**
     * Ritorna il file di configurazione del frame
     * @return workspace
     */
    public File getWindowConfigFile() { return windowConfigFile; }
}
