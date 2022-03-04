import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar
{
    private static Window wParent;

    private JMenu file;
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem exit;

    private JMenu options;
    private JMenu help;

    MenuBar(Window parent)
    {
        wParent = parent;

        file = new JMenu("file");
        open = new JMenuItem("open");
        save = new JMenuItem("salva");
        exit = new JMenuItem("esci");

        options = new JMenu("opzioni");
        help = new JMenu("aiuto");

        add(file);
        file.add(open);
        file.add(save);
        file.add(exit);

        add(options);
        add(help);

        addActionListenerComponent(open, MenuBar::openEvent);
        addActionListenerComponent(save, MenuBar::saveEvent);
        addActionListenerComponent(exit, MenuBar::exitEvent);
        addActionListenerComponent(options, MenuBar::optionsEvent);
        addActionListenerComponent(help, MenuBar::helpEvent);
    }

    private void addActionListenerComponent(JMenuItem item, Runnable method)
    {
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                method.run();
            }
        });
    }

    private static void openEvent()
    {
        System.out.println("To do openEvent...");
    }
    private static void saveEvent()
    {
        System.out.println("To do saveEvent...");
    }
    private static void optionsEvent()
    {
        System.out.println("To do optionsEvent...");
    }
    private static void helpEvent()
    {
        System.out.println("To do helpEvent...");
    }
    private static void exitEvent()
    {
        System.exit(0);
    }
}
