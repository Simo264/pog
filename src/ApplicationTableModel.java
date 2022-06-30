import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

/**
 * TableModel è il modello del componente JTable.
 * Vengono caricati dal file di configurazione "application.config"
 * il numero di righe della tabella
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

    LinkedHashMap<String, String> fileContent = applicationParent.appConfigurationFile.getFileContent();
    nRows = Integer.parseInt(fileContent.get("table-num-rows"));

    addColumns();
    addRows();

    load(applicationParent.appWorkspace.getFileContent());

    addTableModelListener(new TableModelListener()
    {
      public void tableChanged(TableModelEvent e)
      {
        onUpdate();

        applicationParent.appLogger.update(
            String.format("Table has been updated with new values"));
      }
    });
 }


  @Override
  public boolean isCellEditable(int row, int column) { return (column != 0); }

  /**
   * @return il contenuto della tabella in formato LinkedHashMap
   */
  public final LinkedHashMap<String, String> getTableContent()
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
    if(hashMap == null) return;
    if(hashMap.isEmpty()) return;

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
        Object obj = getValueAt(i,j);

        if(obj == null) continue;
        if(obj.toString().isEmpty()) continue;

        // If is numeric
        if(ApplicationUtilities.isNumeric(obj.toString()))
          obj = new ApplicationCellNumeric(
              Double.parseDouble(obj.toString()))
              .resolve();

        // If is formula
        else if (obj.toString().charAt(0) == '=')
        {
          ApplicationCellFormula cellFormula = new ApplicationCellFormula(
                  obj.toString().substring(1)
              );
          cellFormula.setTableModel(this);
          obj = cellFormula.resolve();
          setValueAt(obj, i, j);
        }

        // If is plain String
        else
          obj = new ApplicationCellString(obj.toString()).resolve();

      }
    }
  }
}
