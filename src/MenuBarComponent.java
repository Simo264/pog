import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;


/**
 * MenuBarComponent è il componente menu del frame principale.
 * Presenta i menu File e Help con i rispettivi sottomenu.
 */
public class MenuBarComponent extends JMenuBar
{
    private static Window windowParent;
    private static Workspace workspace;

    private JMenu menuFile;
    private JMenuItem menuNewFile;
    private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuExit;

    private JMenu menuHelp;
    private JMenuItem ghLink;

    MenuBarComponent(Window parent)
    {
        windowParent = parent;
        workspace = windowParent.getWorkspace();

        initMenu();

        addActionListenerComponent(menuNewFile, MenuBarComponent::newFileEvent);
        addActionListenerComponent(menuOpen, MenuBarComponent::openEvent);
        addActionListenerComponent(menuSave, MenuBarComponent::saveEvent);
        addActionListenerComponent(menuExit, MenuBarComponent::exitEvent);
        addActionListenerComponent(ghLink, MenuBarComponent::ghLinkEvent);
    }
    private void initMenu()
    {
        menuFile = new JMenu("File");
        menuNewFile = new JMenuItem("New file");
        menuOpen = new JMenuItem("Open");
        menuSave = new JMenuItem("Save");
        menuExit = new JMenuItem("Exit");
        menuFile.add(menuNewFile);
        menuFile.add(menuOpen);
        menuFile.add(menuSave);
        menuFile.add(menuExit);

        menuHelp = new JMenu("help");
        ghLink = new JMenuItem("Github repository");
        menuHelp.add(ghLink);

        add(menuFile);
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
        workspace.setCurrentWorkspace(fileChooser.getSelectedFile());
        File fileSelected = fileChooser.getSelectedFile();
        FileParser fileParser = new FileParser(fileSelected);
        LinkedHashMap<String, String> hashMap = fileParser.getProperties();
        TableModel tableModel = windowParent.getTablePanel().getTableModel();
        tableModel.emptyTable();
        tableModel.load(hashMap);
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
        workspace.setCurrentWorkspace(file);
        final TableModel tableModel = windowParent.getTablePanel().getTableModel();
        LinkedHashMap<String, String> hashMap = tableModel.getTableContent();
        FileParser fileParser = new FileParser(file);
        fileParser.updateProperties(hashMap);
        // ------------------------
    }
    private static void newFileEvent()
    {
        workspace.setCurrentWorkspace(null);
        windowParent.getTablePanel().getTableModel().emptyTable();
    }
    private static void ghLinkEvent()
    {
        try
        {
            Desktop.getDesktop().browse(new URI("https://github.com/Simo264/pog"));
        }
        catch (IOException e) { e.printStackTrace(System.err); }
        catch (URISyntaxException e) { e.printStackTrace(System.err); }
    }

    private static void exitEvent()
    {
        System.exit(0);
    }
}
