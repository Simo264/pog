import javax.swing.*;
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
public class ApplicationWorkspace extends ApplicationFileWrapper
{
    ApplicationWorkspace(String filename)
    {
        super(ApplicationConfDirPath.getDirectoryPath() + "/" + filename);

        try
        {
            if(!file.exists())
                file.createNewFile();
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(
                    null, e.getMessage(),"IOException", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public LinkedHashMap<String, String> getFileContent()
    {
        return new ApplicationFileParser(this.file).getFileContent();
    }


    /**
     * Salva lo stato dell'attuale workspace
     * @param content
     */
    public void update(LinkedHashMap<String, String> content)
    {
        new ApplicationFileParser(this.file).update(content);
    }
}
