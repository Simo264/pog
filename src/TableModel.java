import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class TableModel extends DefaultTableModel
{
    private static final int COL = 20;
    private static final int ROW = 30;

    TableModel()
    {
        super();
        initModel();
    }

    private void initModel()
    {
        addColumns();
        addRows();
    }
    private void addColumns()
    {
        for (int i = -1; i < COL; i++)
        {
            if(i == -1)
            {
                addColumn("#");
                continue;
            }
            addColumn((char)(i + 'A'));
        }
    }
    private void addRows()
    {
        for (int i = 0; i < ROW; i++)
        {
            addRow(new Vector<String>(20));
            setValueAt(Integer.valueOf(i), i, 0);
        }


        for (int i = 1; i < COL; i++)
        {
            for (int j = 0; j < ROW; j++)
            {
                char letter = (char)(i-1 + 'A');
                String value = String.valueOf(letter) + String.valueOf(j);
                setValueAt(value, j, i);
            }
        }

    }

    public void printContent()
    {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                getValueAt(i, j);

                System.out.println("Cella");
            }
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) { return (column != 0); }
}
