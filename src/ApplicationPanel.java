import javax.swing.*;
import java.awt.*;

/**
 * TablePanel è il pannello principale del frame, contiene la tabella di lavoro
 */
public class ApplicationPanel extends JPanel
{
    private ApplicationTableModel applicationTableModel;
    private JTable table;
    private JScrollPane scrollPane;

    ApplicationPanel(Application applicationParent)
    {
        initPanel(applicationParent);
    }
    private void initPanel(Application applicationParent)
    {
        setLayout(new BorderLayout());

        applicationTableModel = new ApplicationTableModel(applicationParent);
        table = new JTable(applicationTableModel);
        scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
    }

    public ApplicationTableModel getTableModel() { return applicationTableModel; }
}

