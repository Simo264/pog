import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Rappresenta il path della directory configs/.
 */
public class ApplicationPaths
{
    private final static Path CURRENT_DIRECTORY = Paths.get("").toAbsolutePath();

    public static Path getLogDirectoryPath()
    {
        String dir = null;

        // home/simone/Desktop/pog/
        if(ApplicationBuildMode.BUILD_MODE == ApplicationBuildMode.EBuildModes.IDE)
            dir = CURRENT_DIRECTORY.toString() + "/log/";

        // home/simone/Desktop/pog/out/production/pog/
        if(ApplicationBuildMode.BUILD_MODE == ApplicationBuildMode.EBuildModes.CLI)
            dir = CURRENT_DIRECTORY.getParent().getParent().getParent().toString() + "/log/";

        return Paths.get(dir);
    }

    public static Path getConfigDirectoryPath()
    {
        String dir = null;

        // home/simone/Desktop/pog/
        if(ApplicationBuildMode.BUILD_MODE == ApplicationBuildMode.EBuildModes.IDE)
            dir = CURRENT_DIRECTORY.toString() + "/configs/";

        // home/simone/Desktop/pog/out/production/pog/
        if(ApplicationBuildMode.BUILD_MODE == ApplicationBuildMode.EBuildModes.CLI)
            dir = CURRENT_DIRECTORY.getParent().getParent().getParent().toString() + "/configs/";

        return Paths.get(dir);
    }
}
