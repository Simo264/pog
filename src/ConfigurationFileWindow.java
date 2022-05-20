import java.io.File;
import java.io.FileNotFoundException;

/**
 * ConfigurationFileWindow estende la classe astratta ConfigurationFile,
 * rappresenta il file di configurazione del frame JFrame
 *
 */
public class ConfigurationFileWindow extends ConfigurationFile
{
    ConfigurationFileWindow() { super(); }

    /**
     * @return il file di configurazione "window.config"
     * @throws FileNotFoundException
     */
    @Override
    public File getConfigurationFile() throws FileNotFoundException
    {
        File file = new File(CONFIGS_DIRECTORY + "window.config");
        if(!file.exists())
            throw new FileNotFoundException(file.toString() + " does not exist!");

        return file;
    }
}
