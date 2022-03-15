import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ConfigurationFile
{
    private final EBuildModes eBuildModes = EBuildModes.WITH_IDE;
    private final Path CURRENT_PATH = Paths.get("").toAbsolutePath();
    protected String CONFIGS_DIRECTORY;

    ConfigurationFile()
    {
        // home/simone/Desktop/pog/
        if(eBuildModes == EBuildModes.WITH_IDE)
            CONFIGS_DIRECTORY = CURRENT_PATH.toString() + "/configs/";

        // home/simone/Desktop/pog/out/production/pog/
        if(eBuildModes == EBuildModes.WITH_MAKEFILE)
            CONFIGS_DIRECTORY = CURRENT_PATH.getParent().getParent().getParent().toString() + "/configs/";
    }

    public String getConfigDirectory() { return CONFIGS_DIRECTORY; }

    public abstract File getConfigurationFile() throws FileNotFoundException;
}
