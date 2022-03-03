import javax.swing.*;
import java.awt.*;

public class Table extends JPanel
{
    private JTable table;


    String[] columnNames = {
            "First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian"
    };

    Object[][] data = {
            {"Kathy", "Smith", "Snowboarding", 5, false},
            {"John", "Doe", "Rowing", 3, true},
            {"Sue", "Black", "Knitting", 2, false},
            {"Jane", "White", "Speed reading", 20, true},
            {"Joe", "Brown", "Pool", 10, false}
    };

    Table()
    {
        setLayout(new FlowLayout());


        table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
        add(table);
        setVisible(true);
    }

}
