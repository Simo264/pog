import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

public class MenuBarComponent extends JMenuBar
{
    private static Window windowParent;

    private JMenu file;
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem exit;

    private JMenu options;
    private JMenu help;

    MenuBarComponent(Window parent)
    {
        windowParent = parent;

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

        addActionListenerComponent(open, MenuBarComponent::openEvent);
        addActionListenerComponent(save, MenuBarComponent::saveEvent);
        addActionListenerComponent(exit, MenuBarComponent::exitEvent);
        addActionListenerComponent(options, MenuBarComponent::optionsEvent);
        addActionListenerComponent(help, MenuBarComponent::helpEvent);
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

    }
    private static void saveEvent()
    {

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