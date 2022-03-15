import java.io.File;
import java.io.FileNotFoundException;

public class ConfigurationFileTable extends ConfigurationFile
{
    ConfigurationFileTable() { super(); }

    @Override
    public File getConfigurationFile() throws FileNotFoundException
    {
        File file = new File(CONFIGS_DIRECTORY + "table.init.config");
        if(!file.exists())
            throw new FileNotFoundException(file.toString() + " does not exist!");

        return file;
    }
}
