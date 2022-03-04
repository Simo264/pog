import javax.swing.*;
import java.awt.*;

public class TableComponent extends JPanel
{
    private Window windowParent;

    private TableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;

    TableComponent(Window parent)
    {
        windowParent = parent;
        setLayout(new BorderLayout());

        tableModel = new TableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
    }
}

