import java.io.File;
import java.io.FileNotFoundException;

public class ConfigurationFileTableContent extends ConfigurationFile
{
    private File file = null;

    ConfigurationFileTableContent() { super(); }

    @Override
    public File getConfigurationFile() throws FileNotFoundException
    {
        file = new File(CONFIGS_DIRECTORY + "table.content.config");
        if(!file.exists())
            throw new FileNotFoundException();

        return file;
    }
}
