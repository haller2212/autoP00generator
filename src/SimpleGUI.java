import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SimpleGUI extends JFrame {
    public List<Path> files;
    public String dir;
    public List<FilePanel> rows = new ArrayList<>();
    private JButton generateButton = new JButton("generate");

    public SimpleGUI(List<Path> fileNames) {
        super("example!");
        files = fileNames;
        generateButton.addActionListener(new GenerateButtonListener());
        this.setBounds(100, 100, 750, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        int rowscount = fileNames.size();
        container.setLayout(new GridLayout(rowscount + 1, 1));
        for (int i = 0; i < fileNames.size(); i++) {
            rows.add(new FilePanel(String.valueOf(fileNames.get(i).getFileName()), i, this));
        }
        rowsRegenerate(container);
    }

    public void rowsRegenerate(Container container) {
        container.removeAll();
        for (int i = 0; i < rows.size(); i++) {
            container.add(rows.get(i).thispanel);
            rows.get(i).index = i;
        }
        container.add(generateButton);
        container.revalidate();

    }

    class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Main.outputWrite(files);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
