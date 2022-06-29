import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * La classe ApplicationConfigFile estende la classe ApplicationFileWrapper e
 * rappresenta un file di configurazione con il formato key=val.
 */
public class ApplicationConfigFile extends ApplicationFileWrapper <LinkedHashMap<String, String>>
{
  private static final String DEFAULT_FILE_NAME = "application.config";
  private static final String DEFAULT_FILE_PATH =
      ApplicationPaths.getConfigDirectoryPath() + "/" + DEFAULT_FILE_NAME;

  ApplicationConfigFile()
  {
    super(DEFAULT_FILE_PATH);
  }

  /**
   * Legge il file di configurazione e salva i vari attributi in una LinkedHashMap
   * @return il contenuto in formato LinkedHashMap
   */
  public LinkedHashMap<String, String> getFileContent()
  {
    LinkedHashMap<String, String> mapping = new LinkedHashMap<>();
    try
    {
      Scanner scanner = new Scanner(getFile());
      while(scanner.hasNextLine())
      {
        String line = scanner.nextLine();
        if(line.isEmpty()) continue;

        String k = line.split("=")[0];
        String v = line.split("=")[1];
        mapping.put(k, v);
      }
    }
    catch (FileNotFoundException e)  { e.printStackTrace(System.err); }
    catch (ArrayIndexOutOfBoundsException e) { e.printStackTrace(System.err); }
    return mapping;
  }

  /**
   * Aggiorna il file di configurazione con i nuovi attributi
   * @param content
   */
  public void update(LinkedHashMap<String, String> content)
  {
    StringBuffer inputBuffer = new StringBuffer();
    for (Map.Entry<String, String> entry : content.entrySet())
    {
      if(entry.getValue().isEmpty()) continue;
      inputBuffer.append(entry.getKey() + "=" + entry.getValue() + '\n');
    }

    try
    {
      FileWriter fileWriter  = new FileWriter(getFile());
      fileWriter.write(inputBuffer.toString());
      fileWriter.close();
    }
    catch (IOException e) { e.printStackTrace(System.err); }
  }
}
