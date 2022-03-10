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

        defaultFile = new DefaultConfigurationFiles(EFileTypes.TABLE_CONTENT_CONFIG).getFile();
        if(defaultFile.exists())
        {
            FileParser fileParser = new FileParser(defaultFile);
            fillTable(fileParser.getProperties());
        }


        addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                onUpdate();
            }
        });
    }

    private void initTableModel()
    {
        File configFile = new DefaultConfigurationFiles(EFileTypes.TABLE_INIT_CONFIG).getFile();
        FileParser fileParser = new FileParser(configFile);
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
    private void onUpdate()
    {
        for (int i = 0; i < nRows; i++)
        {
            for (int j = 1; j < nCols; j++)
            {
                final SimpleTableCell simpleCell = new SimpleTableCell(getValueAt(i,j));
                if(simpleCell.isValid())
                {
                    switch (simpleCell.classifyValue())
                    {
                        case TEXT:
                        {
                            break;
                        }
                        case FORMULA:
                        {
                            break;
                        }
                        case NUMERIC:
                        {
                            NumericCell numericCell = new NumericCell(simpleCell.getRawObject());

                            break;
                        }
                    }
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
                final SimpleTableCell cell = new SimpleTableCell(getValueAt(i,j));
                if(cell.isValid())
                {
                    Point point = new Point(j-1, i);
                    Coordinate coordinate = new Coordinate(point);
                    map.put(coordinate.toString(), cell.getRawValue());
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
