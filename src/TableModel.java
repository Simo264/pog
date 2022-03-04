import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel
{
    private TableConfig config;

    TableModel()
    {
        super();
        config = new TableConfig(this);
    }

    @Override
    public boolean isCellEditable(int row, int column) { return (column != 0); }

    public void testSetValueConfigFile(String key, String val)
    {
        config.setValueConfigFile(key, val);
    }
}
