import java.io.File;
import java.io.FileNotFoundException;

public class ConfigurationFileTableContent extends ConfigurationFile
{
    ConfigurationFileTableContent() { super(); }

    @Override
    public File getConfigurationFile() throws FileNotFoundException
    {
        File file = new File(CONFIGS_DIRECTORY + "table.content.config");
        if(!file.exists())
            throw new FileNotFoundException(file.toString() + " does not exist!");

        return file;
    }
}
