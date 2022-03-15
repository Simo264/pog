import java.io.File;
import java.io.FileNotFoundException;

public class ConfigurationFileWindow extends ConfigurationFile
{
    private File file = null;

    ConfigurationFileWindow() { super(); }

    @Override
    public File getConfigurationFile() throws FileNotFoundException
    {
        file = new File(CONFIGS_DIRECTORY + "window.init.config");
        if(!file.exists())
            throw new FileNotFoundException();

        return file;
    }
}
