import javax.swing.*;
import java.awt.*;

public class Table extends JPanel
{
    private Window wParent;

    private TableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;

    Table(Window parent)
    {
        wParent = parent;
        setLayout(new BorderLayout());

        initComponents();
        addComponents();
    }

    private void initComponents()
    {
        tableModel = new TableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
    }
    private void addComponents()
    {
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
    }


    public TableModel getTableModel() { return tableModel; }
    public JTable getTable() { return table; }
}

