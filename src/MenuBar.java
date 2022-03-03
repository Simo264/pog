import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar
{
    private JMenu file;
    private JMenuItem save;
    private JMenuItem exit;

    private JMenu options;

    private JMenu help;

    MenuBar()
    {
        file = new JMenu("file");
        save = new JMenuItem("salva");
        exit = new JMenuItem("esci");

        options = new JMenu("opzioni");

        help = new JMenu("aiuto");

        add(file);
        file.add(save);
        file.add(exit);


        add(options);
        add(help);

        addActionListenerComponent(save, MenuBar::saveEvent);
        addActionListenerComponent(exit, MenuBar::exitEvent);
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

    private static void saveEvent()
    {
        System.out.println("Saving...");
    }
    private static void exitEvent()
    {
        System.exit(0);
    }

}
