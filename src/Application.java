import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

/**
 * La classe Application estende JFrame, rappresenta quindi la finestra principale in cui si aggiungeranno i vari
 * componenti quali MenuBarComponent e il TablePanel.
 *
 * Dato il file di configurazione "application.config" che si trova nella cartella configs/, vengono impostate le
 * proprietà del JFrame (es. titolo, dimensione, posizione...)
 */
public class Application extends JFrame
{
    private final String defaultConfigurationFileName = "application.config";
    private final String defaultWorkspaceFileName = "workspace.config";

    /***** Window components ******/
    /******************************/
    public final ApplicationMenuBar applicationMenuBar;
    public final ApplicationPanel applicationPanel;


    /***** Window properties ******/
    /******************************/
    public final LinkedHashMap<String, String> applicationProperties;


    /***** Application workspace ******/
    /******************************/
    public final ApplicationWorkspace workspace;

    private final ApplicationThread autoSaveThread;



    /***** Constructor ******/
    /******************************/
    public Application()
    {
        File configuration = getConfigurationFile();
        applicationProperties = new ApplicationFileParser(configuration).getFileContent();

        workspace = new ApplicationWorkspace(defaultWorkspaceFileName);

        applicationMenuBar = new ApplicationMenuBar(this);
        applicationPanel = new ApplicationPanel(this);

        autoSaveThread = new ApplicationThread(
                applicationPanel.applicationTableModel,
                workspace,
                Double.parseDouble(applicationProperties.get("autosave-time-seconds"))
        );
    }

    public void start()
    {
        setFrameProperties();
        setJMenuBar(applicationMenuBar);
        add(applicationPanel);

        autoSaveThread.start();
    }




    private File getConfigurationFile()
    {
        File configurationFile = null;
        try
        {
            configurationFile = new File(
                    ApplicationConfDirPath.getDirectoryPath() + "/" + defaultConfigurationFileName
            );
            if(!configurationFile.exists())
                throw new FileNotFoundException();
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null,
                    defaultConfigurationFileName + " file not found!",
                    "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        return configurationFile;
    }
    private void setFrameProperties()
    {
        String title = applicationProperties.get( "frame-title");
        int width = Integer.parseInt(applicationProperties.get( "frame-width"));
        int height = Integer.parseInt(applicationProperties.get("frame-height"));
        int posX = Integer.parseInt(applicationProperties.get( "frame-posX"));
        int posY = Integer.parseInt(applicationProperties.get( "frame-posY"));
        boolean resizable = Boolean.parseBoolean(applicationProperties.get( "frame-resizable"));

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

}
