import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultConfigurationFiles
{
    private final BuildModes buildModes = BuildModes.WITH_IDE;
    private Path currentPath = Paths.get("").toAbsolutePath();
    private String configDirectory;

    private String fileName;
    private File configurationFile;

    DefaultConfigurationFiles(EnumFileTypes fileType)
    {
        // home/simone/Desktop/pog/
        if(buildModes == BuildModes.WITH_IDE)
            configDirectory = currentPath.toString() + "/configs/";

        // home/simone/Desktop/pog/out
        if(buildModes == BuildModes.WITH_MAKEFILE)
            configDirectory = currentPath.getParent().toString() + "/configs/";


        switch (fileType)
        {
            case WINDOW_INIT_CONFIG:
                fileName = "window.init.config";
                break;

            case TABLE_INIT_CONFIG:
                fileName = "table.init.config";
                break;

            case TABLE_CONTENT_CONFIG:
                fileName = "configs/table.content.config";
                break;
        }

        configurationFile = new File(configDirectory + fileName);
    }

    public File getFile() { return configurationFile; }
}
