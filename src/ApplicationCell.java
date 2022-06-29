/**
 * Rappresenta un cella di qualsiasi tipo nella tabella.
 * Viene salvato il dato come tipo Object.
 */
public abstract class ApplicationCell <T>
{
  private T data;

  /**
   * @param data
   */
  ApplicationCell(T data)
  {
    this.data = data;
  }


  /**
   * @return data
   */
  public T getData() { return data; }

  /**
   * @return il valore contenuto nella cella
   */
  public abstract T resolve();
}
