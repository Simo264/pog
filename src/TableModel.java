import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
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

    private LinkedHashMap<String, String> windowProperties;

    private int nRows;
    private int nCols;
    private boolean autosave;

    TableModel(Window parent)
    {
        super();

        windowParent = parent;
        windowProperties = windowParent.getWindowProperties();
        workspace = windowParent.getWorkspace();

        setTableProperties();
        loadWorkspace();

        addTableModelListener(tableModelEvent -> {
            onUpdate();

            if(autosave && workspace.getCurrentWorkspace() != null)
                workspace.saveState(getTableContent());
        });
    }

    private void setTableProperties()
    {
        nRows = Integer.parseInt(windowProperties.get("rows"));
        nCols = Integer.parseInt(windowProperties.get("cols"));
        autosave = Boolean.parseBoolean(windowProperties.get("autosave"));

        addColumns();
        addRows();
    }
    private void loadWorkspace()
    {
        File file = workspace.getCurrentWorkspace();
        if(file != null)
        {
            FileParser fileParser = new FileParser(file);
            load(fileParser.getProperties());
        }
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
            setValueAt(i, i, 0);
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
