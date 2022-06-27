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
    //----- Window components -----//
    //-----------------------------//
    public final ApplicationMenuBar applicationMenuBar;
    public final ApplicationPanel applicationPanel;
    //-----------------------------//s

    /**
     * Il workspace dell'applicazione
     */
    public final ApplicationWorkspace workspace;

    /**
     * Il file di configurazione dell'applicazione
     */
    public final ApplicationConfigFile configurationFile;


    /**
     * Thred usato per l'autosalvataggio del workspace
     */
    private final ApplicationThread autoSaveThread;


    /**
     * Il costruttore: viene caricato il contenuto dal file di configurazione
     * application.config, inizializzati i componenti MenuBar, Panel,
     * il workspace e il Thread per l'autosalvataggio
     */
    public Application()
    {
        configurationFile = new ApplicationConfigFile();
        workspace = new ApplicationWorkspace();

        applicationMenuBar = new ApplicationMenuBar(this);
        applicationPanel = new ApplicationPanel(this);

        autoSaveThread = new ApplicationThread(
                applicationPanel.applicationTableModel,
                workspace,
                Double.parseDouble(configurationFile.getFileContent().get("autosave-time-seconds"))
        );
    }

    /**
     * Il metodo start imposta le proprietà del frame,
     * vengono aggiunti i componenti MenuBar e il pannello principale
     * e viene avviato il thread per l'autosalvataggio
     */
    public void start()
    {
        setFrameProperties(configurationFile.getFileContent());
        setJMenuBar(applicationMenuBar);
        add(applicationPanel);

        autoSaveThread.start();
    }


    private void setFrameProperties(LinkedHashMap<String, String> content)
    {
        setTitle(content.get( "frame-title"));

        setPreferredSize(new Dimension(
                Integer.parseInt(content.get( "frame-width")),
                Integer.parseInt(content.get("frame-height"))
        ));

        setLocation(new Point(
                Integer.parseInt(content.get( "frame-posX")),
                Integer.parseInt(content.get( "frame-posY"))
        ));

        setResizable(
                Boolean.parseBoolean(content.get( "frame-resizable"))
        );

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
}
