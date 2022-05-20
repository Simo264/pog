import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class ApplicationWorkspace extends ApplicationFileBase {

    private final String filename = "workspace.ws";

    ApplicationWorkspace(String pathname)
    {


        super(pathname);
    }

}
