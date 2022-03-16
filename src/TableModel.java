import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

/**
 * TableModel è il modello del componente JTable. Vengono caricati dal file di configurazione "table.init.config"
 * i vari attributi della tabella (es. numero righe, numero colonne, autosalvataggio)
 */
public class TableModel extends DefaultTableModel
{
    private Window windowParent;
    private Workspace workspace;

    private LinkedHashMap<String, String> properties;

    private int nRows;
    private int nCols;
    private boolean autosave;

    TableModel(Window parent)
    {
        super();

        loadProperties();
        setTableProperties();

        windowParent = parent;
        workspace = windowParent.getWorkspace();

        File file = workspace.getCurrentWorkspace();
        if(file != null)
        {
            FileParser fileParser = new FileParser(file);
            load(fileParser.getProperties());
        }

        addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                onUpdate();

                if(autosave && workspace.getCurrentWorkspace() != null)
                    workspace.saveState(getTableContent());
            }
        });
    }

    private void loadProperties()
    {
        try
        {
            ConfigurationFileTable configurationFileTable = new ConfigurationFileTable();
            File file = configurationFileTable.getConfigurationFile();
            FileParser fileParser = new FileParser(file);
            properties = fileParser.getProperties();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }
    private void setTableProperties()
    {
        nRows = Integer.parseInt(properties.get("row"));
        nCols = Integer.parseInt(properties.get("col"));
        autosave = Boolean.parseBoolean(properties.get("autosave"));

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
            setValueAt(Integer.valueOf(i), i, 0);
        }
    }
    private void onUpdate()
    {
        for (int i = 0; i < nRows; i++)
        {
            for (int j = 1; j < nCols; j++)
            {
                final TextCell textCell = new TextCell(getValueAt(i,j));
                if(textCell.isValid())
                {
                    if(textCell.getRawString().charAt(0) == '=')
                    {
                        Formula formula = new Formula(textCell.getRawString());
                        String result = formula.resolve(this);
                        setValueAt(result, i, j);
                    }
                }
            }
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) { return (column != 0); }

    /**
     *
     * @return il contenuto della tabella in formato LinkedHashMap
     */
    public final LinkedHashMap<String, String> getTableContent()
    {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < nRows; i++)
        {
            for (int j = 1; j < nCols; j++)
            {
                final Object objectValue = getValueAt(i,j);
                if(objectValue != null)
                {
                    Point point = new Point(j-1, i);
                    Coordinate coordinate = new Coordinate(point);
                    map.put(coordinate.toString(), objectValue.toString());
                }
            }
        }
        return map;
    }

    /**
     * Viene caricato nella tabella il contenuto di hashMap passato come parametro
     * @param hashMap
     */
    public void load(LinkedHashMap<String, String> hashMap)
    {
        for (Map.Entry<String, String> map :hashMap.entrySet())
        {
            Coordinate coordinate = new Coordinate(map.getKey());
            String value = map.getValue();

            Point point = coordinate.reverse();
            setValueAt(value, point.y, point.x+1);
        }
    }

    /**
     * Svuota la tabella
     */
    public void emptyTable()
    {
        for (int i = 0; i < nRows; i++)
            for (int j = 1; j < nCols; j++)
                setValueAt(null, i, j);
    }
}
