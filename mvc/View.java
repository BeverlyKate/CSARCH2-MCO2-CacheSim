package mvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame {
    private JTextField cacheSizeField;
    private JTextField blockSizeField;
    private JTextField readPolicyField;
    private JTextField memorySizeField;
    private JTextField setCountField;
    private JTextField sequenceSizeField;
    private JTextArea sequenceTextArea;
    private JTextArea simLogTextArea;
    private Controller controller;

    public void registerController(Controller controller) {
        this.controller = controller;
    }


    public View() {
        setTitle("Cache Simulator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        //  input fields
        panel.add(new JLabel("Cache Size:"));
        cacheSizeField = new JTextField("32");
        panel.add(cacheSizeField);

        panel.add(new JLabel("Block Size:"));
        blockSizeField = new JTextField("16");
        panel.add(blockSizeField);

        panel.add(new JLabel("Read Policy (NLT or LT):"));
        readPolicyField = new JTextField("NLT");
        panel.add(readPolicyField);

        panel.add(new JLabel("Memory Size (in blocks):"));
        memorySizeField = new JTextField();
        panel.add(memorySizeField);

        panel.add(new JLabel("Set Count:"));
        setCountField = new JTextField("4");
        panel.add(setCountField);

        panel.add(new JLabel("Sequence Size:"));
        sequenceSizeField = new JTextField();
        panel.add(sequenceSizeField);

        panel.add(new JLabel("Sequence Data (comma-separated):"));
        sequenceTextArea = new JTextArea();
        JScrollPane sequenceScrollPane = new JScrollPane(sequenceTextArea);
        panel.add(sequenceScrollPane);

        //buttons
        JButton simulateButton = new JButton("Simulate");
        panel.add(simulateButton);

        //  simulation log area
        simLogTextArea = new JTextArea();
        JScrollPane simLogScrollPane = new JScrollPane(simLogTextArea);
        panel.add(simLogScrollPane);

        //  action listener for the simulate button
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateCache();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void simulateCache() {
        try {
            int cacheSize = Integer.parseInt(cacheSizeField.getText());
            int blockSize = Integer.parseInt(blockSizeField.getText());
            String readPolicy = readPolicyField.getText();
            int memorySize = Integer.parseInt(memorySizeField.getText());
            int setCount = Integer.parseInt(setCountField.getText());

            Cache cache = new Cache(cacheSize, blockSize, readPolicy, memorySize, setCount);

            int sequenceSize = Integer.parseInt(sequenceSizeField.getText());
            int[] sequence = parseSequence(sequenceTextArea.getText());

            ArrayList<String> simLog = cache.simulateCache(sequence);

            // Display simulation log
            simLogTextArea.setText("");
            for (String log : simLog) {
                simLogTextArea.append(log + "\n");
            }

            controller.startSimulation(sequence);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int[] parseSequence(String sequenceData) {
        String[] parts = sequenceData.split(",");
        int[] sequence = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            sequence[i] = Integer.parseInt(parts[i].trim());
        }
        return sequence;
    }

    public int getBlockCount() {
        return Integer.parseInt(cacheSizeField.getText());
    }

    public int getCacheLine() {
        return Integer.parseInt(blockSizeField.getText());
    }

    public String getReadPolicy() {
        return readPolicyField.getText();
    }

    public int getMemoryBlockCount() {
        return Integer.parseInt(memorySizeField.getText());
    }

    public int getSetCount() {
        return Integer.parseInt(setCountField.getText());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new View();
            }
        });
    }
}