import java.io.File;
import java.io.FileNotFoundException;

public class ConfigurationFileWindow extends ConfigurationFile
{
    ConfigurationFileWindow() { super(); }

    @Override
    public File getConfigurationFile() throws FileNotFoundException
    {
        File file = new File(CONFIGS_DIRECTORY + "window.init.config");
        if(!file.exists())
            throw new FileNotFoundException(file.toString() + " does not exist!");

        return file;
    }
}
