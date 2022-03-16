import java.io.File;
import java.io.FileNotFoundException;

/**
 * ConfigurationFileTable estende la classe astratta ConfigurationFile,
 * rappresenta il file di configurazione della tabella di lavoro
 *
 */
public class ConfigurationFileTable extends ConfigurationFile
{
    ConfigurationFileTable() { super(); }

    /**
     * @return il file di configurazione "table.init.config"
     * @throws FileNotFoundException
     */
    @Override
    public File getConfigurationFile() throws FileNotFoundException
    {
        File file = new File(CONFIGS_DIRECTORY + "table.init.config");
        if(!file.exists())
            throw new FileNotFoundException(file.toString() + " does not exist!");

        return file;
    }
}
