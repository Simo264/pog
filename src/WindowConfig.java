import javax.swing.*;
import java.awt.*;
import java.io.File;

public class WindowConfig extends Configuration
{
    private JFrame frame;

    WindowConfig(JFrame target)
    {
        frame = target;
        configFile = new File(System.getProperty("user.dir") + "/res/window.config");

        init();
    }

    protected void init()
    {
        String title = getValueConfigFile( "title");
        int width = Integer.parseInt(getValueConfigFile( "width"));
        int height = Integer.parseInt(getValueConfigFile("height"));
        int posX = Integer.parseInt(getValueConfigFile( "posX"));
        int posY = Integer.parseInt(getValueConfigFile( "posY"));
        boolean visible = Boolean.parseBoolean(getValueConfigFile( "visible"));
        boolean resizable = Boolean.parseBoolean(getValueConfigFile( "resizable"));

        Dimension dimension = new Dimension(width, height);
        Point location = new Point(posX, posY);

        frame.setTitle(title);
        frame.setPreferredSize(dimension);
        frame.setLocation(location);
        frame.setVisible(visible);
        frame.setResizable(resizable);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

}
