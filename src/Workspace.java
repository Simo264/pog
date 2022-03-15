import java.io.File;

public class Workspace
{
    private File currentws;

    Workspace() { currentws = null; }
    Workspace(File file) { currentws = file; }

    public File getCurrentWorkspace() { return currentws; }
    public void setCurrentWorkspace(File file) { currentws = file; }
}
