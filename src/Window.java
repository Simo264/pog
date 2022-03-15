import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

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

    public void start() { this.setVisible(true); }
    public MenuBarComponent getMenuBarComponent() { return menuBarComponent; }
    public TablePanel getTablePanel() { return tablePanel; }
    public Workspace getWorkspace() { return workspace; }
}
