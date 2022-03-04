import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CFileParser
{
    private File configFile;
    private String filePath;

    CFileParser(EnumComponents components)
    {
        if(components == EnumComponents.WINDOW)
            filePath = System.getProperty("user.dir") + "/res/window.init.config";

        else if (components == EnumComponents.TABLE)
            filePath = System.getProperty("user.dir") + "/res/table.init.config";

        configFile = new File(filePath);
    }

    public LinkedHashMap<String, String> getProperties()
    {
        LinkedHashMap<String, String> mappig = new LinkedHashMap<>();
        try
        {
            Scanner scanner = new Scanner(configFile);
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String k = line.split("=")[0];
                String v = line.split("=")[1];
                mappig.put(k, v);
            }
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        return mappig;
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
