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
public class ApplicationMenuBar extends JMenuBar
{
    private static Application applicationParent;

    private JMenu menuFile;
    private JMenuItem menuNewFile;
    private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuExit;

    private JMenu menuHelp;
    private JMenuItem ghLink;

    ApplicationMenuBar(Application parent)
    {
        applicationParent = parent;

        initMenu();

        addActionListenerComponent(menuNewFile, ApplicationMenuBar::newFileEvent);
        addActionListenerComponent(menuOpen, ApplicationMenuBar::openEvent);
        addActionListenerComponent(menuSave, ApplicationMenuBar::saveEvent);
        addActionListenerComponent(menuExit, ApplicationMenuBar::exitEvent);
        addActionListenerComponent(ghLink, ApplicationMenuBar::ghLinkEvent);
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

        int returnVal = fileChooser.showSaveDialog(applicationParent);
        if (returnVal == JFileChooser.CANCEL_OPTION) return ;
        // ------------------------

        // Open configuration

        applicationParent.workspace.setFile(fileChooser.getSelectedFile());
        File fileSelected = fileChooser.getSelectedFile();
        ApplicationFileParser applicationFileParser = new ApplicationFileParser(fileSelected);
        LinkedHashMap<String, String> hashMap = applicationFileParser.getFileContent();
        ApplicationTableModel applicationTableModel = applicationParent.applicationPanel.getTableModel();
        applicationTableModel.emptyTable();
        applicationTableModel.load(hashMap);
        // ------------------------
    }
    private static void saveEvent()
    {
        // Select File
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Saving current configuration *.config", "config"));

        int returnVal = fileChooser.showSaveDialog(applicationParent);
        if (returnVal == JFileChooser.CANCEL_OPTION) return ;

        File file = fileChooser.getSelectedFile();
        if(file.exists())
        {
            int confirmDialog = JOptionPane.showConfirmDialog(applicationParent, "Overwrite selected file?");
            if(confirmDialog != 0) return;
        }
        try
        {
            file.createNewFile();
        }
        catch (IOException e) { e.printStackTrace(); }
        // ------------------------

        // Save configuration
        applicationParent.workspace.setFile(file);
        final ApplicationTableModel applicationTableModel = applicationParent.applicationPanel.getTableModel();
        LinkedHashMap<String, String> hashMap = applicationTableModel.getTableContent();
        ApplicationFileParser applicationFileParser = new ApplicationFileParser(file);
        applicationFileParser.update(hashMap);
        // ------------------------
    }
    private static void newFileEvent()
    {
        applicationParent.workspace.setFile(null);
        applicationParent.applicationPanel.getTableModel().emptyTable();
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
