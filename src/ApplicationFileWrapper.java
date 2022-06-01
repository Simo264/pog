import java.io.File;
import java.util.LinkedHashMap;

/**
 * E' una classe astratta che rappresenta un contenitore per un File generico.
 */
public abstract class ApplicationFileWrapper
{
    protected File file = null;

    ApplicationFileWrapper()    {
    }
    ApplicationFileWrapper(File file)
    {
        this.file = file;
    }
    ApplicationFileWrapper(String filePath)
    {
        file = new File(filePath);
    }


    /**
     * @return File
     */
    public File getFile() { return file; };

    /**
     * Viene settato il file con l'oggetto di tipo File passato come parametro
     * @param file
     */
    public void setFile(File file) { this.file = file; };

    /**
     * @return il contenuto del file nel formato LinkedHashMap [key=val]
     */
    public abstract LinkedHashMap<String, String> getFileContent();

    /**
     * Aggiorna il file: il file viene sovrascritto con contenuto passato come parametro
     * @param content
     */
    public abstract void update(LinkedHashMap<String, String> content);
}
