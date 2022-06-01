import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Rappresenta il path della directory configs/.
 */
public class ApplicationConfDirPath
{
    private final static Path CURRENT_DIRECTORY = Paths.get("").toAbsolutePath();

    public static Path getDirectoryPath()
    {
        String configsDirectory = null;

        // home/simone/Desktop/pog/
        if(ApplicationBuildMode.BUILD_MODE == ApplicationBuildMode.EBuildModes.IDE)
            configsDirectory = CURRENT_DIRECTORY.toString() + "/configs/";

        // home/simone/Desktop/pog/out/production/pog/
        if(ApplicationBuildMode.BUILD_MODE == ApplicationBuildMode.EBuildModes.CLI)
            configsDirectory = CURRENT_DIRECTORY.getParent().getParent().getParent().toString() + "/configs/";

        return Paths.get(configsDirectory);
    }
}
