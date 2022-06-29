import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * ApplicationLogger estende la classe ApplicationFileWrapper, rappresenta il logger
 * dell'applicazione (log/log.txt): vengono segnate tutte le informazioni riguardanti
 * gli eventi dell'utente (apertura/salvataggio di un nuovo workspace, aggiornamento tabella...),
 * informazioni su crash del programma e informazioni generali.
 */
public class ApplicationLogger extends ApplicationFileWrapper <String>
{
  private static final String FILE_NAME = "log.txt";
  private static final String FILE_PATH =
      ApplicationPaths.getLogDirectoryPath() + "/" + FILE_NAME;


  ApplicationLogger()
  {
    super(FILE_PATH);

    if(!getFile().exists())
    {
      try
      {
        getFile().createNewFile();
      }
      catch (IOException e)
      {
        JOptionPane.showMessageDialog(
            null, e.getMessage(),"IOException", JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
      }
    }
  }

  /**
   * Ritorna il contenuto del file log.txt in formato String
   * @return String
   */
  public String getFileContent()
  {
    StringBuilder stringBuilder = new StringBuilder();
    try
    {
      Scanner scanner = new Scanner(getFile());
      while (scanner.hasNextLine())
        stringBuilder.append(scanner.nextLine() + "\n");

      scanner.close();
    }
    catch (FileNotFoundException e)
    {
      throw new RuntimeException(e);
    }
    return stringBuilder.toString();
  }

  /**
   * Appende al file log.txt la stringa passata come parametro
   * @param log
   */
  public void update(String log)
  {
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    try
    {
      FileWriter fileWriter = new FileWriter(getFile(), true);
      fileWriter.write(String.format("[%s] %s\n", formattedDate, log));
      fileWriter.close();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }
}
