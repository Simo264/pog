import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public abstract class Configuration
{
    protected File configFile;

    protected abstract void init();


    private boolean configFileContainsKey(String key)
    {
        try
        {
            Scanner scanner = new Scanner(configFile);
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String k = line.split("=")[0];
                String v = line.split("=")[1];
                if(k.contains(key))
                    return true;
            }
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        return false;
    }
    protected void setValueConfigFile(String key, String value)
    {
        if(!configFileContainsKey(key))
        {
            System.out.println("Key is not in config file!");
            return ;
        }

        try
        {
            StringBuffer inputBuffer = new StringBuffer();
            Scanner scanner = new Scanner(configFile);
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String k = line.split("=")[0];
                if(k.contains(key))
                    break;

                inputBuffer.append(line + '\n');
            }

            inputBuffer.append(key + "=" + value + '\n');

            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                inputBuffer.append(line + '\n');
            }

            FileOutputStream fileOut = new FileOutputStream(configFile);
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
    }
    protected String getValueConfigFile(String key)
    {
        try
        {
            Scanner scanner = new Scanner(configFile);

            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String k = line.split("=")[0];
                String v = line.split("=")[1];
                if(k.contains(key))
                    return v;
            }
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        return null;
    }
}
