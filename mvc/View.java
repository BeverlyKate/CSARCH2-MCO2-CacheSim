package mvc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class View extends JFrame {
    private JTextField cacheSizeField;
    private JTextField blockSizeField;
    private JTextField readPolicyField;
    private JTextField memorySizeField;
    private JTextField setCountField;
    private JTextField sequenceSizeField;
    private JTextArea sequenceTextArea;
    private JTextArea simLogTextArea;
    private JLabel currentSetField;
    private JLabel currentBlockField;
    private JLabel currentSequenceField;
    private JLabel currentSetLabel;
    private JLabel currentBlockLabel;
    private JLabel currentSequenceLabel;
    private Controller controller;
    private JTable cacheTable;
    private JTable sequenceTable;
    private JButton nextButton;
    private JButton prevButton;
    private JButton skipToEndButton;
    private DefaultTableModel sequenceTableModel;
    private DefaultTableModel cacheTableModel;
    private JSplitPane tablePanes;

    private JLabel emptylabel;

    public void registerController(Controller controller) {
        this.controller = controller;
    }

    public View() {
        setTitle("Cache Simulator");
        setSize(1080, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainPanel.setResizeWeight(0.25);

        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        leftSplitPane.setResizeWeight(0.5);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(11, 2));
        JPanel simLogPanel = new JPanel(new BorderLayout());

        JPanel rightPanel= new JPanel();
        mainPanel.setRightComponent(rightPanel);

        JPanel controlPanel = new JPanel(new FlowLayout());
        tablePanes= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        tablePanes.setResizeWeight(0.25);
        //rightPanel.add(controlPanel, BorderLayout.NORTH);
        //rightPanel.add(tablePanes, BorderLayout.CENTER);

        sequenceTable = new JTable();
        this.sequenceTableModel = new DefaultTableModel(new Object[]{"Main memory"}, 0);
        sequenceTable.setModel(sequenceTableModel);
        setColumnWidths(sequenceTable);
        JScrollPane sequenceTableScrollPane = new JScrollPane(sequenceTable);
        tablePanes.setRightComponent(sequenceTableScrollPane);


        cacheTable = new JTable();
        this.cacheTableModel = new DefaultTableModel(new Object[]{"Cache memory"}, 0);
        cacheTable.setModel(cacheTableModel);
        setColumnWidths(cacheTable);
        JScrollPane cacheTableScrollPane = new JScrollPane(cacheTable);
        tablePanes.setLeftComponent(cacheTableScrollPane);


        inputPanel.add(new JLabel("Cache Size (in blocks):"));
        cacheSizeField = new JTextField("32");
        inputPanel.add(cacheSizeField);

        inputPanel.add(new JLabel("Block Size (in words):"));
        blockSizeField = new JTextField("16");
        inputPanel.add(blockSizeField);

        inputPanel.add(new JLabel("Read Policy (NLT or LT):"));
        readPolicyField = new JTextField("NLT");
        inputPanel.add(readPolicyField);

        inputPanel.add(new JLabel("Memory Size (in blocks):"));
        memorySizeField = new JTextField();
        inputPanel.add(memorySizeField);

        inputPanel.add(new JLabel("Set size:"));
        setCountField = new JTextField("4");
        inputPanel.add(setCountField);

        inputPanel.add(new JLabel("Sequence Size:"));
        sequenceSizeField = new JTextField();
        inputPanel.add(sequenceSizeField);

        inputPanel.add(new JLabel("Sequence Data (comma-separated):"));
        sequenceTextArea = new JTextArea();
        JScrollPane sequenceScrollPane = new JScrollPane(sequenceTextArea);
        inputPanel.add(sequenceScrollPane);

        inputPanel.add(new JLabel("Current set: "));
        currentSetField = new JLabel();
        inputPanel.add(currentSetField);

        inputPanel.add(new JLabel("Current Block: "));
        currentBlockField = new JLabel();
        inputPanel.add(currentBlockField);

        inputPanel.add(new JLabel("Current Sequence: "));
        currentSequenceField = new JLabel();
        inputPanel.add(currentSequenceField);

        JButton simulateButton = new JButton("Simulate");
        inputPanel.add(simulateButton);

        emptylabel= new JLabel();
        inputPanel.add(emptylabel);

        simLogTextArea = new JTextArea("Sim log will appear here.");
        JScrollPane simLogScrollPane = new JScrollPane(simLogTextArea);
        simLogPanel.add(simLogScrollPane);

        leftSplitPane.setTopComponent(inputPanel);
        leftSplitPane.setBottomComponent(simLogPanel);
        mainPanel.setLeftComponent(leftSplitPane);

        cacheTable = new JTable();
        JScrollPane cacheScrollPane = new JScrollPane(cacheTable);
        tablePanes.setLeftComponent(cacheScrollPane);

        sequenceTable = new JTable();
        rightPanel.add(tablePanes, BorderLayout.CENTER);
        rightPanel.add(controlPanel, BorderLayout.NORTH);

        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");
        skipToEndButton = new JButton("Skip to End");

        controlPanel.add(prevButton);
        controlPanel.add(nextButton);
        controlPanel.add(skipToEndButton);

        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateCache();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        skipToEndButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        add(mainPanel);
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
    
            simLogTextArea.setText("");
            for (String log : simLog) {
                simLogTextArea.append(log + "\n");
            }
    
            // Display cache data
            Set[] cacheArray = cache.getCache();
            Object[][] cacheData = new Object[cacheArray.length * cacheArray[0].getBlocks()][2];
    
            int row = 0;
            for (Set set : cacheArray) {
                int[][] setArr = set.getSetArr();
                for (int blockIndex = 0; blockIndex < set.getBlocks(); blockIndex++) {
                    cacheData[row][0] = setArr[blockIndex][Set.DATA_COL]; // Data
                    cacheData[row][1] = setArr[blockIndex][Set.AGE_COL]; // Age
                    row++;
                }
            }
    
            // Bold border indices
            List<Integer> boldBorderIndices = new ArrayList<>();
            for (int i = 0; i < cacheData.length; i++) {
                if (i % setCount == 0 && i != 0) {
                    boldBorderIndices.add(i - 1); // Index of the last row in each set
                }
            }
    
            cacheTableModel = new DefaultTableModel(cacheData, new Object[]{"Data", "Age"});
            cacheTable.setModel(cacheTableModel);
    
            JScrollPane cacheScrollPane = new JScrollPane(cacheTable);
            tablePanes.setLeftComponent(cacheScrollPane);
    
            controller.startSimulation(sequence);
    
            sequenceTableModel.setRowCount(0);
            for (int i = 0; i < sequence.length; i++) {
                sequenceTableModel.addRow(new Object[]{sequence[i]});
            }
            sequenceTable.setModel(sequenceTableModel);
    
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setColumnWidths(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        int columnCount = columnModel.getColumnCount();
    
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setMaxWidth(5 * 15); // Adjust the multiplier and constant as needed
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

    public void updateGUI(int sequenceData, int setIndex, int foundBlockIndex) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                currentSetField.setText("" + setIndex);
                currentBlockField.setText("" + foundBlockIndex);
                currentSequenceLabel.setText("" + sequenceData);

                revalidate();
                repaint();
            }
        });
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
