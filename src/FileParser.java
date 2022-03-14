import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileParser
{
    private File file;

    FileParser(File inputFile)
    {
        file = inputFile;
    }

    public LinkedHashMap<String, String> getProperties()
    {
        LinkedHashMap<String, String> mapping = new LinkedHashMap<>();
        try
        {
            Scanner scanner = new Scanner(file);
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
            FileWriter fileWriter  = new FileWriter(file);
            fileWriter.write(inputBuffer.toString());
            fileWriter.close();
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}