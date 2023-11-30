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
    private JButton simulateButton;
    private JTable cacheTable;
    private JTable sequenceTable;
    private JButton nextButton;
    private JButton prevButton;
    private JButton skipToEndButton;
    private int currentStep;

    public void registerController(Controller controller) {
        this.controller = controller;
    }

    public View() {
        setTitle("Cache Simulator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2));

        // Add input fields
        inputPanel.add(new JLabel("Cache Size:"));
        cacheSizeField = new JTextField("32");
        inputPanel.add(cacheSizeField);

        inputPanel.add(new JLabel("Block Size:"));
        blockSizeField = new JTextField("16");
        inputPanel.add(blockSizeField);

        inputPanel.add(new JLabel("Read Policy (NLT or LT):"));
        readPolicyField = new JTextField("NLT");
        inputPanel.add(readPolicyField);

        inputPanel.add(new JLabel("Memory Size (in blocks):"));
        memorySizeField = new JTextField();
        inputPanel.add(memorySizeField);

        inputPanel.add(new JLabel("Set Count:"));
        setCountField = new JTextField("4");
        inputPanel.add(setCountField);

        inputPanel.add(new JLabel("Sequence Size:"));
        sequenceSizeField = new JTextField();
        inputPanel.add(sequenceSizeField);

        inputPanel.add(new JLabel("Sequence Data (comma-separated):"));
        sequenceTextArea = new JTextArea();
        JScrollPane sequenceScrollPane1 = new JScrollPane(sequenceTextArea);
        inputPanel.add(sequenceScrollPane1);

        simulateButton = new JButton("Simulate");
        inputPanel.add(simulateButton);

        mainPanel.add(inputPanel, BorderLayout.WEST);

        // Create cache table
        cacheTable = new JTable();
        // ... (initialize table model and configure as needed)
        JScrollPane cacheScrollPane = new JScrollPane(cacheTable);
        mainPanel.add(cacheScrollPane, BorderLayout.CENTER);

        // Create sequence table
        sequenceTable = new JTable();
        // (initialize table model)
        JScrollPane sequenceScrollPane = new JScrollPane(sequenceTable);
        mainPanel.add(sequenceScrollPane, BorderLayout.EAST);

        // Create a panel for simulation log
        JPanel simLogPanel = new JPanel(new BorderLayout());
        simLogTextArea = new JTextArea();
        JScrollPane simLogScrollPane = new JScrollPane(simLogTextArea);
        simLogPanel.add(simLogScrollPane, BorderLayout.CENTER);

        // Add simulation log panel to the south
        mainPanel.add(simLogPanel, BorderLayout.SOUTH);

        // Add control buttons
        JPanel controlPanel = new JPanel(new FlowLayout());
        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");
        skipToEndButton = new JButton("Skip to End");

        controlPanel.add(prevButton);
        controlPanel.add(nextButton);
        controlPanel.add(skipToEndButton);

        // Add control panel to the north
        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Set action listener for the simulate button
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateCache();
            }
        });

        // Set action listeners for control buttons
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to show the next step
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to show the previous step
            }
        });

        skipToEndButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to skip to the end
            }
        });

        // Add main panel to the frame
        add(mainPanel);

        // Set the frame visible after all components are added
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

    public void updateSimulationResults(ArrayList<String> simLog) {
        for (String msg : simLog) {
            simLogTextArea.append(msg + "\n");
        }
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
