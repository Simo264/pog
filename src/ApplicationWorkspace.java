import java.io.*;
import java.util.LinkedHashMap;

/**
 * La classe Workspace estende ApplicationFileWrapper e rappresenta il file
 * di lavoro in cui si sta lavorando. Il file è serializzato.
 *
 * All'avvio del programma il workspace di default sarà il file "configs/workspace.bin".
 *
 * Durante l'esecuzione del programma l'utente potrà decidere se aprire
 * un nuovo spazio di lavoro e il workspace sarà il nuovo file aperto.
 */
public class ApplicationWorkspace extends ApplicationFileWrapper
{
    private static final String DEFAULT_FILE_NAME = "workspace.bin";
    private static final String DEFAULT_FILE_PATH =
            ApplicationPaths.getConfigDirectoryPath() + "/" + DEFAULT_FILE_NAME;

    public ApplicationWorkspace()
    {
        super(DEFAULT_FILE_PATH);
    }

    ApplicationWorkspace(File file)
    {
        super(file);
    }

    /**
     * Deserializzo il file.
     * @return il contenuto all'interno del workspace nel formato LinkedHashMap
     */
    public LinkedHashMap<String, String> getFileContent()
    {
        LinkedHashMap<String, String> content = null;

        try
        {
            FileInputStream fileInputStream = new FileInputStream(getFile());
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            content = (LinkedHashMap<String, String>) in.readObject();
            fileInputStream.close();
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        return content;
    }


    /**
     * Serializzo il file.
     * Salva lo stato dell'attuale workspace.
     * @param content
     */
    public void update(LinkedHashMap<String, String> content)
    {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(getFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(content);
            out.close();
            fileOutputStream.close();
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
