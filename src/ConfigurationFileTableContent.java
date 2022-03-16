import java.io.File;
import java.io.FileNotFoundException;

/**
 * ConfigurationFileTableContent estende la classe astratta ConfigurationFile,
 * rappresenta il file di default del workspace.
 *
 */
public class ConfigurationFileTableContent extends ConfigurationFile
{
    ConfigurationFileTableContent() { super(); }

    /**
     * @return il file di default del workspace "table.init.config"
     * @throws FileNotFoundException
     */
    @Override
    public File getConfigurationFile() throws FileNotFoundException
    {
        File file = new File(CONFIGS_DIRECTORY + "table.content.config");
        if(!file.exists())
            throw new FileNotFoundException(file.toString() + " does not exist!");

        return file;
    }
}
