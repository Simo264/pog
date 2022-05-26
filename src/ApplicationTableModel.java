import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

/**
 * TableModel è il modello del componente JTable.
 * Vengono caricati dal file di configurazione "application.config"
 * il numero di righe e il numero di colonne della tabella
 */
public class ApplicationTableModel extends DefaultTableModel
{
    private Application applicationParent;

    private int nRows;
    private final int NUM_COL = 26;

    ApplicationTableModel(Application parent)
    {
        super();

        applicationParent = parent;

        nRows = Integer.parseInt(applicationParent.applicationProperties.get("table-num-rows"));
        addColumns();
        addRows();

        loadWorkspace();

/*
        addTableModelListener(tableModelEvent -> {
            onUpdate();

            if(applicationParent.workspace.getFile() != null)
                applicationParent.workspace.update(getTableContent());
        });
*/


    }


    @Override
    public boolean isCellEditable(int row, int column) { return (column != 0); }

    /**
     * @return il contenuto della tabella in formato LinkedHashMap
     */
    public final LinkedHashMap<String, String> getContent()
    {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < nRows; i++)
        {
            for (int j = 1; j < NUM_COL; j++)
            {
                final Object objectValue = getValueAt(i,j);
                if(objectValue != null)
                {
                    Point point = new Point(j-1, i);
                    ApplicationCoordinate applicationCoordinate = new ApplicationCoordinate(point);
                    map.put(applicationCoordinate.toString(), objectValue.toString());
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
        for (Map.Entry<String, String> map : hashMap.entrySet())
        {
            ApplicationCoordinate applicationCoordinate = new ApplicationCoordinate(map.getKey());
            String value = map.getValue();

            Point point = applicationCoordinate.reverse();
            setValueAt(value, point.y, point.x+1);
        }
    }

    /**
     * Svuota la tabella
     */
    public void emptyTable()
    {
        for (int i = 0; i < nRows; i++)
            for (int j = 1; j < NUM_COL; j++)
                setValueAt(null, i, j);
    }



    private void loadWorkspace()
    {
        File file = applicationParent.workspace.getFile();
        if(file != null)
        {
            ApplicationFileParser applicationFileParser = new ApplicationFileParser(file);
            load(applicationFileParser.getFileContent());
        }
    }
    private void addColumns()
    {
        addColumn("#");
        for (int i = 0; i < NUM_COL; i++)
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
            for (int j = 1; j < NUM_COL; j++)
            {
                final TextCell textCell = new TextCell(getValueAt(i,j));
                if(!textCell.isValid()) continue;

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
