import javax.swing.table.DefaultTableModel;
import java.util.LinkedHashMap;
import java.util.Vector;

public class TableModel extends DefaultTableModel
{
    private CFileParser fileParser;
    private LinkedHashMap<String, String> properties;

    private int nRows;
    private int nCols;
    private boolean isEmpty;

    TableModel()
    {
        super();

        fileParser = new CFileParser(EnumComponents.TABLE);
        properties = fileParser.getProperties();

        nRows = Integer.parseInt(properties.get("row"));
        nCols = Integer.parseInt(properties.get("col"));
        isEmpty = Boolean.parseBoolean(properties.get("is-empty"));

        addColumns();
        addEmptyRows();

        if(!isEmpty)
            fillRows();
    }

    @Override
    public boolean isCellEditable(int row, int column) { return (column != 0); }

    private void addColumns()
    {
        addColumn("#");
        for (int i = 0; i < nCols; i++)
            addColumn((char)(i + 'A'));
    }
    private void addEmptyRows()
    {
        for (int i = 0; i < nRows; i++)
        {
            addRow(new Vector<String>(nRows));
            setValueAt(Integer.valueOf(i), i, 0);
        }
    }
    private void fillRows()
    {

    }

}
