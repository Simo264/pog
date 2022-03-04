import javax.swing.*;
import java.awt.*;

public class Table extends JPanel
{
    private TableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;

    Table()
    {
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

