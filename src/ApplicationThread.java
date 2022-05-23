
public class ApplicationThread extends java.lang.Thread {
    private long timeOld;
    private long timeNew;

    private ApplicationWorkspace workspace;

    private ApplicationTableModel applicationTableModel;

    private final int timeInSecond = 1;

    ApplicationThread(ApplicationTableModel model, ApplicationWorkspace ws)
    {
        applicationTableModel = model;
        workspace = ws;

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
                if(workspace.getWorkspace() != null)
                    workspace.update(applicationTableModel.getTableContent());

                timeOld = timeNew;
            }
        }
    }
}
