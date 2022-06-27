import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * E' una classe astratta che rappresenta un wrapper di un generico File.
 */
public abstract class ApplicationFileWrapper
{
    private File file;

    ApplicationFileWrapper(File file)
    {
        this.file = file;
    }
    ApplicationFileWrapper(String filePath)
    {
        file = new File(filePath);

        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(
                    null, e.getMessage(),"IOException", JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }
        }
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
