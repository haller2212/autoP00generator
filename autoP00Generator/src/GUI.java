import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

public class GUI extends JFrame {
    private JPanel jPanel = new JPanel();
    public JTextField textField =  new JTextField("out");
    public List<Path> files;
    public String dir;
    public List<FilePanel> rows = new ArrayList<>();
    private JButton generateButton = new JButton("generate");

    public GUI(List<Path> fileNames) {
        super("example!");
        files = fileNames;
        generateButton.addActionListener(new GenerateButtonListener());
        this.setBounds(100, 100, 750, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        int rowscount = fileNames.size();
        container.setLayout(new GridLayout(rowscount + 2, 1));
        for (int i = 0; i < fileNames.size(); i++) {
            rows.add(new FilePanel(fileNames.get(i), i, this));
        }
        jPanel.add(new JLabel("output name:"));
        jPanel.add(textField);
        jPanel.setLayout(new GridLayout(1, 2));
        rowsRegenerate(container);
    }

    public void rowsRegenerate(Container container) {
        container.removeAll();
        for (int i = 0; i < rows.size(); i++) {
            container.add(rows.get(i).thispanel);
            rows.get(i).index = i;
        }
        container.add(jPanel);
        container.add(generateButton);
        container.revalidate();

    }

    class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (rows.size() == 0){
                JOptionPane.showMessageDialog(null, "Все файлы были удаленны", "error", JOptionPane.PLAIN_MESSAGE);
            }

            List<Path> files2 = new ArrayList<>();
            for (int i = 0; i < rows.size(); i++) {
                files2.add(rows.get(i).filee);
            }
            try {
                Main.outputWrite(files2, textField.getText());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
