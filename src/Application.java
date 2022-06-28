import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;

/**
 * La classe Application estende JFrame, rappresenta quindi la finestra principale in cui si aggiungeranno i vari
 * componenti quali MenuBarComponent e il TablePanel.
 *
 * Dato il file di configurazione "configs/application.config", vengono impostate le
 * proprietà del JFrame (es. titolo, dimensione, posizione...)
 */
public class Application extends JFrame
{
  //----- Window components -----//
  //-----------------------------//
  public final ApplicationMenuBar appMenuBar;
  public final ApplicationPanel appPanel;
  //-----------------------------//

  /**
   * Il workspace dell'applicazione
   */
  public final ApplicationWorkspace appWorkspace;

  /**
   * Il file di configurazione dell'applicazione
   */
  public final ApplicationConfigFile appConfigurationFile;

  /**
   * Il Logger dell'applicazione
   */
  public final ApplicationLogger appLogger;


  /**
   * Thred usato per l'autosalvataggio del workspace
   */
  private final ApplicationThread autoSaveThread;


  /**
   * Il costruttore della classe Application, vengono inizializzate
   * tutte le componenti necessarie
   */
  public Application()
  {
    appConfigurationFile = new ApplicationConfigFile();
    appWorkspace = new ApplicationWorkspace();
    appLogger = new ApplicationLogger();

    appMenuBar = new ApplicationMenuBar(this);
    appPanel = new ApplicationPanel(this);

    autoSaveThread = new ApplicationThread(
        appPanel.applicationTableModel,
        appWorkspace,
        Double.parseDouble(appConfigurationFile.getFileContent().get("autosave-time-seconds"))
    );

    appLogger.update("Application initialized!");
  }

  /**
   * Il metodo start imposta le proprietà del frame,
   * vengono aggiunti i componenti MenuBar e il pannello principale
   * e viene avviato il thread per l'autosalvataggio
   */
  public void start()
  {
    setFrameProperties(appConfigurationFile.getFileContent());
    setJMenuBar(appMenuBar);
    add(appPanel);

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
