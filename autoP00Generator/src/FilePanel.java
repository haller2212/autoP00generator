import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public class FilePanel {
    public Path filee;
    private GUI simpleGUI;
    public int index;
    public JPanel thispanel = new JPanel();
    public JLabel label = new JLabel();
    private JButton upButton = new JButton("up");
    private JButton downButton = new JButton("down");
    private JButton removeButton = new JButton("remove");

    public FilePanel(Path file, int indexx, GUI gui) {
        simpleGUI = gui;
        index = indexx;
        filee = file;
        thispanel.setLayout(new GridLayout(1, 4));
        String labelText = "";
        try {
            labelText = file.getFileName().toString() + " - " + Main.getCoordSys(file);
        } catch (FileNotFoundException e) {
        }
        label.setText(labelText);
        thispanel.add(label);
        upButton.addActionListener(new UpButtonListener());
        thispanel.add(upButton);
        downButton.addActionListener(new DownButtonListener());
        thispanel.add(downButton);
        removeButton.addActionListener(new RemoveButtonListener());
        thispanel.add(removeButton);
    }

    class UpButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FilePanel seThis = simpleGUI.rows.get(index - 1);
            simpleGUI.rows.set(index - 1, simpleGUI.rows.get(index));
            simpleGUI.rows.set(index, seThis);
            simpleGUI.rowsRegenerate(simpleGUI.getContentPane());

            try {
                Path secThis = simpleGUI.files.get(index - 1);
                simpleGUI.files.set(index - 1, simpleGUI.files.get(index));
                simpleGUI.files.set(index, secThis);
            }catch (IndexOutOfBoundsException exception){}
        }
    }

    class DownButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FilePanel seThis = simpleGUI.rows.get(index + 1);
            simpleGUI.rows.set(index + 1, simpleGUI.rows.get(index));
            simpleGUI.rows.set(index, seThis);
            simpleGUI.rowsRegenerate(simpleGUI.getContentPane());

            try {
                Path secThis = simpleGUI.files.get(index + 1);
                simpleGUI.files.set(index + 1, simpleGUI.files.get(index));
                simpleGUI.files.set(index, secThis);
            }catch (IndexOutOfBoundsException exception){}
        }
    }

    class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            simpleGUI.rows.remove(index);
            simpleGUI.rowsRegenerate(simpleGUI.getContentPane());
            simpleGUI.files.remove(index);
        }
    }
}
