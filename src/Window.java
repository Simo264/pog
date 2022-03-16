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
    private LinkedHashMap<String, String> properties;
    private MenuBarComponent menuBarComponent;
    private TablePanel tablePanel;

    public Window()
    {
        workspace = new Workspace();

        initProperties();
        setWindowProperties();
        addWindowComponent();
    }

    private void initProperties()
    {
        try
        {
            ConfigurationFileWindow configurationFileWindow = new ConfigurationFileWindow();
            File file = configurationFileWindow.getConfigurationFile();
            FileParser fileParser = new FileParser(file);
            properties = fileParser.getProperties();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }
    private void setWindowProperties()
    {
        String title = properties.get( "title");
        int width = Integer.parseInt(properties.get( "width"));
        int height = Integer.parseInt(properties.get("height"));
        int posX = Integer.parseInt(properties.get( "posX"));
        int posY = Integer.parseInt(properties.get( "posY"));
        boolean visible = Boolean.parseBoolean(properties.get( "visible"));
        boolean resizable = Boolean.parseBoolean(properties.get( "resizable"));

        Dimension dimension = new Dimension(width, height);
        Point location = new Point(posX, posY);

        setTitle(title);
        setPreferredSize(dimension);
        setLocation(location);
        setVisible(visible);
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
}
