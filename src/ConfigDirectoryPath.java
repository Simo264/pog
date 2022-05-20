import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigDirectoryPath
{
    private final Path CURRENT_PATH = Paths.get("").toAbsolutePath();
    private String CONFIGS_DIRECTORY;


    public String getConfigDirectory()
    {
        // home/simone/Desktop/pog/
        if(ApplicationBuildMode.BUILD_MODE == ApplicationBuildMode.EBuildModes.WITH_IDE)
            CONFIGS_DIRECTORY = CURRENT_PATH.toString() + "/configs/";

        // home/simone/Desktop/pog/out/production/pog/
        if(ApplicationBuildMode.BUILD_MODE == ApplicationBuildMode.EBuildModes.WITH_MAKEFILE)
            CONFIGS_DIRECTORY = CURRENT_PATH.getParent().getParent().getParent().toString() + "/configs/";

        return CONFIGS_DIRECTORY;
    }
}
