import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

        initMenu();

        addActionListenerComponent(open, MenuBarComponent::openEvent);
        addActionListenerComponent(save, MenuBarComponent::saveEvent);
        addActionListenerComponent(exit, MenuBarComponent::exitEvent);
        addActionListenerComponent(options, MenuBarComponent::optionsEvent);
        addActionListenerComponent(help, MenuBarComponent::helpEvent);
    }
    private void initMenu()
    {
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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(".config Files", "config"));

        int returnVal = fileChooser.showSaveDialog(windowParent);
        if (returnVal == JFileChooser.CANCEL_OPTION) return ;

        File fileSelected = fileChooser.getSelectedFile();
        CFileParser fileParser = new CFileParser(fileSelected);
        LinkedHashMap<String, String> hashMap = fileParser.getProperties();

        TableModel tableModel = windowParent.getTableComponent().getTableModel();
        tableModel.fillTable(hashMap);
    }
    private static void saveEvent()
    {
        final TablePanel table = windowParent.getTableComponent();
        final TableModel tableModel = table.getTableModel();
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();

        for (int i = 0; i < tableModel.getRowCount(); i++)
        {
            for (int j = 1; j < tableModel.getColumnCount(); j++)
            {
                final Object cellContent = tableModel.getValueAt(i,j);
                if(cellContent != null)
                {
                    Point point = new Point(j-1, i);
                    Coordinate coordinate = new Coordinate(point);
                    hashMap.put(coordinate.toString(), cellContent.toString());
                }
            }
        }

        File configFile = new DefaultConfigurationFiles(EnumFileTypes.TABLE_CONTENT_CONFIG).getFile();
        CFileParser fileParser = new CFileParser(configFile);
        fileParser.updateProperties(hashMap);
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
