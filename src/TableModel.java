import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class TableModel extends DefaultTableModel
{
    private int nRows;
    private int nCols;

    TableModel()
    {
        super();
        initTableModel();
    }

    private void initTableModel()
    {
        File configFile = new DefaultConfigurationFiles(EnumFileTypes.TABLE_INIT_CONFIG).getFile();
        CFileParser fileParser = new CFileParser(configFile);
        LinkedHashMap<String, String> properties = fileParser.getProperties();

        nRows = Integer.parseInt(properties.get("row"));
        nCols = Integer.parseInt(properties.get("col"));

        addColumns();
        addRows();
    }
    private void addColumns()
    {
        addColumn("#");
        for (int i = 0; i < nCols; i++)
            addColumn((char)(i + 'A'));
    }
    private void addRows()
    {
        for (int i = 0; i < nRows; i++)
        {
            addRow(new Vector<String>(nRows));
            setValueAt(Integer.valueOf(i+1), i, 0);
        }
    }


    @Override
    public boolean isCellEditable(int row, int column) { return (column != 0); }

    public void fillTable(LinkedHashMap<String, String> hashMap)
    {
        for (Map.Entry<String, String> map :hashMap.entrySet())
        {
            Coordinate coordinate = new Coordinate(map.getKey());
            String value = map.getValue();

            Point point = coordinate.reverse();
            setValueAt(value, point.y, point.x+1);
        }
    }

}
