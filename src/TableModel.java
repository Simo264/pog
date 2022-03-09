import javax.sound.midi.SysexMessage;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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

    private File defaultFile;

    TableModel()
    {
        super();
        initTableModel();

        defaultFile = new DefaultConfigurationFiles(EnumFileTypes.TABLE_CONTENT_CONFIG).getFile();
        if(defaultFile.exists())
        {
            CFileParser fileParser = new CFileParser(defaultFile);
            fillTable(fileParser.getProperties());
        }


        addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                updateTable();
            }
        });


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
    private void updateTable()
    {
        for (int i = 0; i < nRows; i++)
        {
            for (int j = 1; j < nCols; j++)
            {
                final Object cellContent = getValueAt(i,j);
                if(cellContent != null)
                {
                    Point point = new Point(j-1, i);
                    Coordinate coordinate = new Coordinate(point);
                    System.out.println(coordinate.toString());
                }
            }
        }
    }



    @Override
    public boolean isCellEditable(int row, int column) { return (column != 0); }

    public final LinkedHashMap<String, String> getTableContent()
    {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < nRows; i++)
        {
            for (int j = 1; j < nCols; j++)
            {
                final Object cellContent = getValueAt(i,j);
                if(cellContent != null)
                {
                    Point point = new Point(j-1, i);
                    Coordinate coordinate = new Coordinate(point);
                    map.put(coordinate.toString(), cellContent.toString());
                }
            }
        }
        return map;
    }
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
