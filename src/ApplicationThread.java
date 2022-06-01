
public class ApplicationThread extends java.lang.Thread
{
    private double timeInSecond;
    private long timeOld;
    private long timeNew;

    private ApplicationWorkspace workspace;
    private ApplicationTableModel applicationTableModel;



    ApplicationThread(ApplicationTableModel tableModel, ApplicationWorkspace ws, double autosaveTime)
    {
        applicationTableModel = tableModel;
        workspace = ws;

        timeInSecond = autosaveTime;
        timeOld = System.currentTimeMillis();
        timeNew = timeOld;
    }

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
