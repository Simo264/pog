import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

public class SettingsFrame extends JFrame
{
    private LinkedHashMap<String, String> windowProperties;

    private Window windowParent;
    private JPanel panel;
    private JButton btnSave;

    SettingsFrame(Window parent)
    {
        windowParent = parent;
        windowProperties = windowParent.getWindowProperties();

        initFrame();
        initPanel();

        btnSave.addActionListener(actionEvent -> {

        });
    }


    private void initFrame()
    {
        setTitle("Settings");
        setPreferredSize(new Dimension(250, 500));
        setLocation(new Point(500,200));
        pack();
        setVisible(true);
    }

    private void initPanel()
    {
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        panel = new JPanel(new GridLayout(windowProperties.size()+1, 2, 0, 20));
        panel.setBorder(padding);

        windowProperties.forEach((key, value) -> {
            JLabel label = new JLabel(key);
            JTextField textField = new JTextField(value);
            panel.add(label);
            panel.add(textField);
        });

        btnSave = new JButton("Apply changes");
        panel.add(new JLabel());
        panel.add(btnSave);

        add(panel, BorderLayout.CENTER);
    }

}
