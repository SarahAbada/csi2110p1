import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * MainUI provides an interactive interface for the Island Lake Survey application
 * Allows users to analyze island and lake data from input files
 */
public class MainUI extends JFrame {
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JButton analyzeButton;
    private JButton clearButton;
    private JButton loadFileButton;
    private JButton aboutButton;
    private Partition<Integer> partition;
    
    public MainUI() {
        setTitle("Island Lake Survey - Main Application");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadItem = new JMenuItem("Load File");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        loadItem.addActionListener(e -> loadFile());
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
        
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel with title
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Island Lake Survey Analyzer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Center panel with split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        
        // Input panel (left side)
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(new TitledBorder("Input Data"));
        
        inputArea = new JTextArea();
        inputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        inputArea.setLineWrap(false);
        JScrollPane inputScrollPane = new JScrollPane(inputArea);
        inputPanel.add(inputScrollPane, BorderLayout.CENTER);
        
        JPanel inputButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loadFileButton = new JButton("Load File");
        loadFileButton.addActionListener(e -> loadFile());
        inputButtonPanel.add(loadFileButton);
        inputPanel.add(inputButtonPanel, BorderLayout.SOUTH);
        
        splitPane.setLeftComponent(inputPanel);
        
        // Output panel (right side)
        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputPanel.setBorder(new TitledBorder("Analysis Results"));
        
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputPanel.add(outputScrollPane, BorderLayout.CENTER);
        
        splitPane.setRightComponent(outputPanel);
        
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        // Bottom panel with buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        analyzeButton = new JButton("Analyze Data");
        analyzeButton.setFont(new Font("Arial", Font.BOLD, 14));
        analyzeButton.setPreferredSize(new Dimension(150, 35));
        analyzeButton.addActionListener(e -> analyzeData());
        
        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 35));
        clearButton.addActionListener(e -> clearAll());
        
        aboutButton = new JButton("About");
        aboutButton.setPreferredSize(new Dimension(100, 35));
        aboutButton.addActionListener(e -> showAbout());
        
        buttonPanel.add(analyzeButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(aboutButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Add sample data hint
        inputArea.setText("# Enter island data or load from file\n# Format:\n# rows cols\n# grid data (0=water, 1=land)\n# Example:\n4 5\n0 1 1 0 0\n1 1 0 0 1\n0 0 0 1 1\n0 1 0 0 0");
    }
    
    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Select Input File");
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                inputArea.setText(content.toString());
                outputArea.setText("File loaded: " + file.getName() + "\nReady for analysis.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error loading file: " + ex.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void analyzeData() {
        String inputText = inputArea.getText().trim();
        if (inputText.isEmpty() || inputText.startsWith("#")) {
            outputArea.setText("Please enter valid island data or load a file.");
            return;
        }
        
        try {
            outputArea.setText("Analyzing island and lake data...\n\n");
            
            Scanner scanner = new Scanner(inputText);
            List<List<Integer>> data = new ArrayList<>();
            partition = new Partition<>();
            
            String firstline = scanner.nextLine();
            while (firstline.startsWith("#")) {
                firstline = scanner.nextLine();
            }
            
            String[] dimensions = firstline.trim().split("\\s+");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            Node<Integer>[][] nodes = new Node[rows][cols];
            
            // Read grid data
            for (int i = 0; i < rows; i++) {
                String line = scanner.nextLine().trim();
                while (line.startsWith("#")) {
                    line = scanner.nextLine().trim();
                }
                
                List<Integer> row = new ArrayList<>();
                if (line.contains(" ")) {
                    String[] tokens = line.trim().split("\\s+");
                    for (int j = 0; j < cols; j++) {
                        row.add(Integer.parseInt(tokens[j]));
                    }
                } else {
                    for (int j = 0; j < cols; j++) {
                        char c = line.charAt(j);
                        row.add(Character.isDigit(c) ? Character.getNumericValue(c) : 0);
                    }
                }
                data.add(row);
            }
            
            // Create clusters
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int val = data.get(i).get(j);
                    String clusterId = (val == 0) ? "water" : "land";
                    Node<Integer> pos = partition.makeCluster(val, clusterId, i, j);
                    nodes[i][j] = pos;
                }
            }
            
            // Union adjacent cells
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Node<Integer> curNode = nodes[i][j];
                    int curValue = data.get(i).get(j);
                    if (i > 0 && data.get(i - 1).get(j).equals(curValue)) {
                        partition.union(curNode, nodes[i - 1][j]);
                    }
                    if (j > 0 && data.get(i).get(j - 1).equals(curValue)) {
                        partition.union(curNode, nodes[i][j - 1]);
                    }
                }
            }
            
            // Count islands
            int islandCount = 0;
            List<Integer> sizes = new ArrayList<>();
            for (Cluster<Integer> cluster : partition.clusters) {
                if (partition.element(cluster.leader).equals(1)) {
                    islandCount++;
                    sizes.add(cluster.sequence.size());
                }
            }
            sizes.sort((a, b) -> b - a);
            
            // Calculate total area
            int totalArea = 0;
            for (int size : sizes) {
                totalArea += size;
            }
            
            // Display results
            StringBuilder result = new StringBuilder();
            result.append("=== ANALYSIS RESULTS ===\n\n");
            result.append("Grid Size: ").append(rows).append(" x ").append(cols).append("\n");
            result.append("Number of Islands: ").append(islandCount > 0 ? islandCount : -1).append("\n\n");
            
            if (islandCount > 0) {
                result.append("Island Sizes (descending): ");
                for (int size : sizes) {
                    result.append(size).append(" ");
                }
                result.append("\n\n");
                result.append("Total Land Area: ").append(totalArea).append(" cells\n");
                result.append("Average Island Size: ").append(String.format("%.2f", (double) totalArea / islandCount)).append(" cells\n");
            }
            
            result.append("\n=== PARTITION INFO ===\n");
            result.append("Total Clusters: ").append(partition.numberOfClusters()).append("\n");
            
            outputArea.setText(result.toString());
            scanner.close();
            
        } catch (Exception ex) {
            outputArea.setText("Error analyzing data:\n" + ex.getMessage() + 
                "\n\nPlease check your input format.");
            ex.printStackTrace();
        }
    }
    
    private void clearAll() {
        inputArea.setText("");
        outputArea.setText("");
    }
    
    private void showAbout() {
        String message = "Island Lake Survey Analyzer\n\n" +
                        "This application analyzes island and lake data using\n" +
                        "partition-based clustering algorithms.\n\n" +
                        "Features:\n" +
                        "• Load data from files\n" +
                        "• Analyze island formations\n" +
                        "• Calculate areas and statistics\n\n" +
                        "Login credentials:\n" +
                        "Username: admin\n" +
                        "Password: password123";
        
        JOptionPane.showMessageDialog(this,
            message,
            "About Island Lake Survey",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default look and feel
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }
}
