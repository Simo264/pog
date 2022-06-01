/**
 * Rappresenta un thread che periodicamente salva nel workspace il contenuto della tabella.
 */
public class ApplicationThread extends java.lang.Thread
{
    private double timeInSecond;
    private long timeOld;
    private long timeNew;

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
        timeOld = System.currentTimeMillis();
        timeNew = timeOld;
    }

    /**
     * Avvia il thread per il salvataggio automatico
     */
    public void run()
    {
        while(true)
        {
            timeNew = System.currentTimeMillis();
            if(timeNew - timeOld >= timeInSecond * 1000)
            {
                workspace.update(applicationTableModel.getContent());
                timeOld = timeNew;
            }
        }
    }
}
