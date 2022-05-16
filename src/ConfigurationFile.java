import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * La classe atratta ConfigurationFile costruisce il path per la directory configs/.
 *
 * I path sono diversi dal modo in cui si esegue il programma:
 * se eseguito dall'IDE avrà un path diverso rispetto se eseguito da CLI
 */
public abstract class ConfigurationFile
{
    private enum EBuildModes { WITH_IDE, WITH_MAKEFILE }

    //private final EBuildModes eBuildModes = EBuildModes.WITH_IDE;
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

    /**
     * Ritorna il percorso dove si trova la directory configs/. Il valore di ritorno è una Stringa
     * @return CONFIGS_DIRECTORY
     */
    public String getConfigDirectory() { return CONFIGS_DIRECTORY; }

    public abstract File getConfigurationFile() throws FileNotFoundException;



}
