import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationConfDirPath
{
    private final static Path CURRENT_DIRECTORY = Paths.get("").toAbsolutePath();

    public static Path getDirectoryPath()
    {
        String configsDirectory = null;

        // home/simone/Desktop/pog/
        if(ApplicationBuildMode.BUILD_MODE == ApplicationBuildMode.EBuildModes.WITH_IDE)
            configsDirectory = CURRENT_DIRECTORY.toString() + "/configs/";

        // home/simone/Desktop/pog/out/production/pog/
        if(ApplicationBuildMode.BUILD_MODE == ApplicationBuildMode.EBuildModes.WITH_MAKEFILE)
            configsDirectory = CURRENT_DIRECTORY.getParent().getParent().getParent().toString() + "/configs/";

        return Paths.get(configsDirectory);
    }
}
