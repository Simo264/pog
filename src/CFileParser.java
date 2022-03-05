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

    private File inputFile;
    private String fileName;

    CFileParser(EnumFileTypes components)
    {
        // home/simone/Desktop/pog/
        if(buildModes == BuildModes.WITH_IDE)
            resDirectory = currentPath.toString() + "/res/";

        // home/simone/Desktop/pog/out
        else if(buildModes == BuildModes.WITH_MAKEFILE)
            resDirectory = currentPath.getParent().toString() + "/res/";


        switch (components)
        {
            case WINDOW_INIT_CONFIG:
                fileName = "window.init.config";
                break;

            case TABLE_INIT_CONFIG:
                fileName = "table.init.config";
                break;

            case TABLE_CONTENT_CONFIG:
                fileName = "table.content.config";
                break;
        }

        inputFile = new File(resDirectory + fileName);
    }

    public LinkedHashMap<String, String> getProperties()
    {
        LinkedHashMap<String, String> mapping = new LinkedHashMap<>();
        try
        {
            Scanner scanner = new Scanner(inputFile);
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
            FileWriter fileWriter  = new FileWriter(inputFile);
            fileWriter.write(inputBuffer.toString());
            fileWriter.close();
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}
