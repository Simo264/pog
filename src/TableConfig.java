import java.io.File;
import java.util.Vector;

public class TableConfig extends Configuration
{
    private TableModel model;

    TableConfig(TableModel target)
    {
        configFile = new File(System.getProperty("user.dir") + "/res/table.config");
        model = target;

        init();
    }

    protected void init()
    {
        int nRow = Integer.parseInt(getValueConfigFile("row"));
        int nCol = Integer.parseInt(getValueConfigFile("col"));
        boolean isEmpty = Boolean.parseBoolean(getValueConfigFile("is-empty"));

        addColumns(nCol);
        addEmptyRows(nRow);
        fillRowsTest(nRow, nCol);
    }

    private void addColumns(int nCol)
    {
        for (int i = -1; i < nCol; i++)
        {
            if(i == -1)
            {
                model.addColumn("#");
                continue;
            }
            model.addColumn((char)(i + 'A'));
        }
    }
    private void addEmptyRows(int nRow)
    {
        for (int i = 0; i < nRow; i++)
        {
            model.addRow(new Vector<String>(20));
            model.setValueAt(Integer.valueOf(i), i, 0);
        }
    }
    private void fillRowsTest(int nRow, int nCol)
    {
        for (int i = 1; i <= nCol; i++)
        {
            for (int j = 0; j < nRow; j++)
            {
                char letter = (char)(i-1 + 'A');
                String value = String.valueOf(letter) + String.valueOf(j);
                model.setValueAt(value, j, i);
            }
        }
    }
}
