import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedHashMap;

public class Window extends JFrame {
    private File configFile;
    private CFileParser fileParser;
    private LinkedHashMap<String, String> properties;

    private MenuBarComponent menuBarComponent;
    private TablePanel tablePanel;

    public Window() {
        configFile = new DefaultConfigurationFiles(EnumFileTypes.WINDOW_INIT_CONFIG).getFile();
        fileParser = new CFileParser(configFile);
        properties = fileParser.getProperties();
        setWindowProperties();

        menuBarComponent = new MenuBarComponent(this);
        tablePanel = new TablePanel(this);
        setJMenuBar(menuBarComponent);
        add(tablePanel);
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

    public void start() { this.setVisible(true); }
    public MenuBarComponent getMenuBarComponent() { return menuBarComponent; }
    public TablePanel getTablePanel() { return tablePanel; }

}
