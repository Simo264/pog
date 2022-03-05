import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CFileParser
{
    private final BuildModes buildModes = BuildModes.WITH_IDE;
    private Path currentPath = Paths.get("").toAbsolutePath();
    private String resDirectory;

    private File configFile;
    private String fileName;

    CFileParser(EnumComponents components)
    {
        // home/simone/Desktop/pog/
        if(buildModes == BuildModes.WITH_IDE)
            resDirectory = currentPath.toString() + "/res/";

        // home/simone/Desktop/pog/out
        else if(buildModes == BuildModes.WITH_MAKEFILE)
            resDirectory = currentPath.getParent().toString() + "/res/";


        if(components == EnumComponents.WINDOW)
            fileName = "window.init.config";

        else if (components == EnumComponents.TABLE)
            fileName = "table.init.config";

        configFile = new File(resDirectory + fileName);
    }

    public LinkedHashMap<String, String> getProperties()
    {
        LinkedHashMap<String, String> mapping = new LinkedHashMap<>();
        try
        {
            Scanner scanner = new Scanner(configFile);
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String k = line.split("=")[0];
                String v = line.split("=")[1];
                mapping.put(k, v);
            }
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        return mapping;
    }
    public void updateProperties(LinkedHashMap<String, String> props)
    {
        StringBuffer inputBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : props.entrySet())
            inputBuffer.append(entry.getKey() + "=" + entry.getValue() + '\n');

        try
        {
            FileWriter fileWriter  = new FileWriter(configFile);
            fileWriter.write(inputBuffer.toString());
            fileWriter.close();
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}
