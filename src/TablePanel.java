import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel
{
    private Window windowParent;

    private TableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;

    TablePanel(Window parent)
    {
        windowParent = parent;

        setLayout(new BorderLayout());

        tableModel = new TableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
    }

    public TableModel getTableModel() { return tableModel; }
}

