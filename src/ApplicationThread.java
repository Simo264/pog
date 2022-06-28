/**
 * Rappresenta un thread che periodicamente salva nel workspace il contenuto della tabella.
 */
public class ApplicationThread extends java.lang.Thread
{
  private double timeInSecond;
  private ApplicationWorkspace workspace;
  private ApplicationTableModel applicationTableModel;


  /**
   * @param tableModel per ottenere il contenuto della tabella
   * @param ws per aggiornare il workspace
   * @param autosaveTime il tempo in secondi per il salvataggio automatico
   */
  ApplicationThread(ApplicationTableModel tableModel, ApplicationWorkspace ws, double autosaveTime)
  {
    applicationTableModel = tableModel;
    workspace = ws;

    timeInSecond = autosaveTime;

    setDaemon(true);
  }

  /**
   * Avvia il thread per il salvataggio automatico
   */
  public void run()
  {
    autosave();
  }


  private void autosave()
  {
    long Told = System.currentTimeMillis();
    long Tnew;

    while(true)
    {
      Tnew = System.currentTimeMillis();
      if((Tnew - Told) >= (timeInSecond * 10e2))
      {
        workspace.update(applicationTableModel.getTableContent());
        Told = System.currentTimeMillis();
      }
    }
  }
}
