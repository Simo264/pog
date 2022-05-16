
public class MyThread extends java.lang.Thread {
    private long timeOld;
    private long timeNew;

    private Workspace workspace;

    private TableModel tableModel;

    private final int timeInSecond = 1;

    MyThread(TableModel model, Workspace ws)
    {
        tableModel = model;
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
                if(workspace.getCurrentWorkspace() != null)
                {
                    workspace.saveState(tableModel.getTableContent());
                    System.out.println("auto save...");
                }
                timeOld = timeNew;
            }
        }
    }
}
