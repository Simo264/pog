import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

/**
 * La classe Workspace rappresenta il file di lavoro in cui si sta lavorando.
 * All'avvio del programma il workspace di default sarà il file (se esiste)
 * "workspace.ws" all'interno della cartella configs/, altrimenti null.
 *
 * Durante l'esecuzione del programma l'utente potrà decidere se aprire
 * un nuovo spazio di lavoro e il workspace sarà il nuovo file creato.
 */
public class Workspace
{
    private File currentws;

    Workspace()
    {
        try
        {
            ConfigurationFileTableContent configurationFileTableContent = new ConfigurationFileTableContent();
            currentws = configurationFileTableContent.getConfigurationFile();
        }
        catch (FileNotFoundException e)
        {
            currentws = null;
        }
    }

    /**
     * Ritorna il workspace corrente
     * @return currentws
     */
    public File getCurrentWS() { return currentws; }

    /**
     * Imposta il workspace con un nuovo file
     * @param file
     */
    public void setCurrentWS(File file) { currentws = file; }

    /**
     * Salva lo stato dell'attuale workspace
     * @param content
     */
    public void update(LinkedHashMap<String, String> content)
    {
        if(currentws == null)
        {
            System.out.println("[Error in Workspace.saveState] file is null!");
            return;
        }

        FileParser fileParser = new FileParser(currentws);
        fileParser.updateProperties(content);
    }

}
