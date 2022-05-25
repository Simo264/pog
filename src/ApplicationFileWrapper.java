import java.io.File;
import java.util.LinkedHashMap;

public abstract class ApplicationFileWrapper
{
    protected File file = null;

    ApplicationFileWrapper()    {
    }
    ApplicationFileWrapper(File file)
    {
        this.file = file;
    }
    ApplicationFileWrapper(String filePath)
    {
        file = new File(filePath);
    }



    public File getFile() { return file; };
    public void setFile(File file) { this.file = file; };

    public abstract LinkedHashMap<String, String> getFileContent();

    public abstract void update(LinkedHashMap<String, String> content);
}
