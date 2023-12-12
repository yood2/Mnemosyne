package ui;

import model.*;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static ui.Mnemosyne.printLog;

public class TitleUI extends JFrame {

    private GridBagConstraints gbc;
    private Deck deck;
    private final String filePath;

    // EFFECTS: Constructs TitleUI class and initializes fields and swing components
    public TitleUI(String filePath) {
        super("Mnemosyne");
        this.filePath = filePath;
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: Initializes default window settings and calls methods responsible
    //          for component creation.
    private void initializeGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(Mnemosyne.WIDTH, Mnemosyne.HEIGHT));

        addShutdownFunction();

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        createTitleLabel();
        createButtonPanel();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addShutdownFunction() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Execute your method before shutting down
                printLog();
                // Close the application
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Initializes settings for TitleLabel and adds to frame.
    private void createTitleLabel() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Mnemosyne");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, gbc);
    }

    // MODIFIES: this
    // EFFECTS: Initializes settings for ButtonPanel and buttons
    //          and adds to frame.
    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        createNewDeckButton(buttonPanel);
        createLoadDeckButton(buttonPanel);
        createQuitButton(buttonPanel);

        gbc.gridy = 1;
        add(buttonPanel, gbc);
    }

    // MODIFIES: this
    // EFFECTS: Initializes New Deck Button and adds to button panel.
    private void createNewDeckButton(JPanel buttonPanel) {
        JButton newDeckButton = new JButton("New Deck");
        newDeckButton.addActionListener(e -> {
            dispose();
            deck = new Deck("New Deck");
            new WorkspaceUI(deck, filePath);
        });
        buttonPanel.add(newDeckButton);
    }

    // MODIFIES: this
    // EFFECTS: Initializes Load Deck Button and adds to button panel.
    private void createLoadDeckButton(JPanel buttonPanel) {
        JButton loadDeckButton = new JButton("Load Deck");
        loadDeckButton.addActionListener(e -> {
            dispose();
            load();
            new WorkspaceUI(deck, filePath);
        });
        buttonPanel.add(loadDeckButton);
    }

    // MODIFIES: this
    // EFFECTS: Initializes Quit Button and adds to button panel.
    private void createQuitButton(JPanel buttonPanel) {
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(quitButton);
    }

    // MODIFIES: this
    // EFFECTS: load Card and Deck objects from JSON file
    private void load() {
        JsonReader jsonReader = new JsonReader(filePath);
        try {
            deck = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + filePath);
        }
    }
}
