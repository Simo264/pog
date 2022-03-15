import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

public class Workspace
{
    private File currentws;

    Workspace()
    {
        try
        {
            ConfigurationFileTableContent configurationFileTableContent = new ConfigurationFileTableContent();
            currentws = configurationFileTableContent.getConfigurationFile();
        }
        catch (FileNotFoundException e)
        {
            currentws = null;
        }
    }

    public File getCurrentWorkspace() { return currentws; }
    public void setCurrentWorkspace(File file) { currentws = file; }
    public void saveState(LinkedHashMap<String, String> content)
    {
        if(currentws == null) return;

        FileParser fileParser = new FileParser(currentws);
        fileParser.updateProperties(content);
    }

}
