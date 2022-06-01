import javax.swing.*;
import java.awt.*;

/**
 * TablePanel è il pannello principale del frame, contiene la tabella di lavoro
 */
public class ApplicationPanel extends JPanel
{
    public final ApplicationTableModel applicationTableModel;
    public final JTable table;
    private JScrollPane scrollPane;

    ApplicationPanel(Application applicationParent)
    {
        applicationTableModel = new ApplicationTableModel(applicationParent);
        table = new JTable(applicationTableModel);

        initPanel();
    }
    private void initPanel()
    {
        setLayout(new BorderLayout());

        scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
    }
}

