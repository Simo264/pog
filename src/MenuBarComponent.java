import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

public class MenuBarComponent extends JMenuBar
{
    private static Window windowParent;

    private JMenu menuFile;
    private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuExit;
    private JMenu menuOptions;
    private JMenu menuHelp;

    MenuBarComponent(Window parent)
    {
        windowParent = parent;

        initMenu();

        addActionListenerComponent(menuOpen, MenuBarComponent::openEvent);
        addActionListenerComponent(menuSave, MenuBarComponent::saveEvent);
        addActionListenerComponent(menuExit, MenuBarComponent::exitEvent);
        addActionListenerComponent(menuOptions, MenuBarComponent::optionsEvent);
        addActionListenerComponent(menuHelp, MenuBarComponent::helpEvent);
    }
    private void initMenu()
    {
        menuFile = new JMenu("file");
        menuOpen = new JMenuItem("open");
        menuSave = new JMenuItem("salva");
        menuExit = new JMenuItem("esci");

        menuOptions = new JMenu("opzioni");
        menuHelp = new JMenu("aiuto");

        add(menuFile);
        menuFile.add(menuOpen);
        menuFile.add(menuSave);
        menuFile.add(menuExit);

        add(menuOptions);
        add(menuHelp);
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
        // Select File
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.config Files", "config"));

        int returnVal = fileChooser.showSaveDialog(windowParent);
        if (returnVal == JFileChooser.CANCEL_OPTION) return ;
        // ------------------------

        // Open configuration
        File fileSelected = fileChooser.getSelectedFile();
        CFileParser fileParser = new CFileParser(fileSelected);
        LinkedHashMap<String, String> hashMap = fileParser.getProperties();
        TableModel tableModel = windowParent.getTablePanel().getTableModel();
        tableModel.fillTable(hashMap);
        // ------------------------
    }
    private static void saveEvent()
    {
        // Select File
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Saving current configuration *.config", "config"));

        int returnVal = fileChooser.showSaveDialog(windowParent);
        if (returnVal == JFileChooser.CANCEL_OPTION) return ;

        File file = fileChooser.getSelectedFile();
        if(file.exists())
        {
            int confirmDialog = JOptionPane.showConfirmDialog(windowParent, "Overwrite selected file?");
            if(confirmDialog != 0) return;
        }
        try
        {
            file.createNewFile();
        }
        catch (IOException e) { e.printStackTrace(); }
        // ------------------------

        // Save configuration
        final TableModel tableModel = windowParent.getTablePanel().getTableModel();
        LinkedHashMap<String, String> hashMap = tableModel.getTableContent();
        CFileParser fileParser = new CFileParser(file);
        fileParser.updateProperties(hashMap);
        // ------------------------
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
