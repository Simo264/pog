import java.io.File;

/**
 * E' una classe astratta che rappresenta un wrapper di un generico File.
 */
public abstract class ApplicationFileWrapper <T>
{
  private File file;

  ApplicationFileWrapper(String filePath)
  {
    file = new File(filePath);
  }


  /**
   * @return File
   */
  public File getFile() { return file; };

  /**
   * Viene settato il file con l'oggetto di tipo File passato come parametro
   * @param file
   */
  public void setFile(File file) { this.file = file; };


  /**
   * @return il contenuto del file
   */
  public abstract T getFileContent();

  /**
   * Aggiornamento del contenuto del file
   * @param content
   */
  public abstract void update(T content);


}
