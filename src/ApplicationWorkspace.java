import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * La classe Workspace rappresenta il file di lavoro in cui si sta lavorando.
 * All'avvio del programma il workspace di default sarà il file (se esiste)
 * "workspace.config" all'interno della cartella configs/, altrimenti null.
 *
 * Durante l'esecuzione del programma l'utente potrà decidere se aprire
 * un nuovo spazio di lavoro e il workspace sarà il nuovo file creato.
 */
public class ApplicationWorkspace
{
    private final String defaultWSName = "/workspace.config";
    private File workspace = null;

    ApplicationWorkspace()
    {
        try
        {
            workspace = new File(ConfigDirectoryPath.getDirectoryPath() + defaultWSName);
            if(!workspace.exists())
                workspace.createNewFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ritorna il workspace corrente
     * @return currentws
     */
    public File getWorkspace() { return workspace; }

    /**
     * Imposta il workspace con un nuovo file
     * @param file
     */
    public void setWorkspace(File file) { workspace = file; }

    /**
     * Salva lo stato dell'attuale workspace
     * @param content
     */
    public void update(LinkedHashMap<String, String> content)
    {
        if(workspace == null)
        {
            System.out.println("[Error in Workspace.saveState] file is null!");
            return;
        }
        FileParser fileParser = new FileParser(workspace);
        fileParser.updateProperties(content);
    }

}
