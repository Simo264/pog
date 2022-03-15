import java.io.File;
import java.io.FileNotFoundException;

public class ConfigurationFileTable extends ConfigurationFile
{
    private File file = null;

    ConfigurationFileTable() { super(); }

    @Override
    public File getConfigurationFile() throws FileNotFoundException
    {
        file = new File(CONFIGS_DIRECTORY + "table.init.config");
        if(!file.exists())
            throw new FileNotFoundException();

        return file;
    }
}
