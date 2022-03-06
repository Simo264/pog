import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TablePanel extends JPanel
{
    private Window windowParent;

    private TableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;

    TablePanel(Window parent)
    {
        windowParent = parent;
        initTable();


        table.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                // TODO
                // ...
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                // TODO
                // ...
            }
        });
    }

    private void initTable()
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

    public TableModel getTableModel() { return tableModel; }
}

