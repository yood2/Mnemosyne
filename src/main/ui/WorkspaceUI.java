package ui;

import model.Card;
import model.Deck;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.List;

import static ui.Mnemosyne.printLog;

public class WorkspaceUI extends JFrame {

    private final Deck deck;
    private final String filePath;
    private GridBagConstraints gbc;
    private JTable cardTable;
    private DefaultTableModel tableModel;

    // EFFECTS: Constructs PracticeUI class and initializes fields and swing components
    public WorkspaceUI(Deck deck, String filePath) {
        super("Workspace");
        this.deck = deck;
        this.filePath = filePath;
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: Initializes swing components and default window settings
    private void initializeGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(Mnemosyne.WIDTH, Mnemosyne.HEIGHT));

        addShutdownFunction();

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        initializeCardTable();
        initializeButtonPanel();

        pack();
        setLocationRelativeTo(null);  // Center the frame
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
    // EFFECTS: Initializes TableModel to hold cards. Updates model with
    //          cards and their information.
    private void initializeCardTable() {
        // Create table model with 3 columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 1 && column != 2 && column != 3;
            }
        };

        tableModel.addColumn("Index");
        tableModel.addColumn("Question");
        tableModel.addColumn("Answer");
        tableModel.addColumn("Correct");

        // Add data from cards into table model
        refreshCardTable();

        // Create a table with the model
        cardTable = new JTable(tableModel);

        // Set preferred column widths
        cardTable.getColumnModel().getColumn(0).setPreferredWidth(5); // Smaller width for the "Index" column
        cardTable.getColumnModel().getColumn(1).setPreferredWidth(300); // Question column
        cardTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Answer column
        cardTable.getColumnModel().getColumn(3).setPreferredWidth(5);  // # Practiced column

        JScrollPane scrollPane = new JScrollPane(cardTable);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        add(scrollPane, gbc);
    }

    // MODIFIES: this
    // EFFECTS: Updates model with cards and their information.
    public void refreshCardTable() {
        List<Card> cards = deck.getAllCards();

        // Clear existing rows in table model
        tableModel.setRowCount(0);

        // Add data from cards into table model
        int currIndex = 0;
        for (Card card : cards) {
            currIndex++;
            tableModel.addRow(new Object[]{currIndex, card.getQuestion(),
                    card.getAnswer(), card.getCorrectlyAnswered()});
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates buttonPanel and adds to frame.
    //          Create buttons + lambda functions and adds to panel.
    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        addButton(buttonPanel, "New Card", e -> handleNewCard());
        addButton(buttonPanel, "Delete Card", e -> handleDeleteCard());
        addButton(buttonPanel, "Reset Attempts", e -> handleResetAttempts());
        addButton(buttonPanel, "Practice All", e -> handlePracticeAll());
        addButton(buttonPanel, "Practice New", e -> handlePracticeNew());
        addButton(buttonPanel, "Save", e -> handleSave());
        addButton(buttonPanel, "Quit", e -> handleQuit());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(buttonPanel, gbc);
    }

    // MODIFIES: this
    // EFFECTS: Creates a button with new ActionListener.
    private void addButton(Container container, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        container.add(button);
    }

    // MODIFIES: this
    // EFFECTS: Accepts input from user and creates a new Card object.
    //          Adds the card to the table model and refresh.
    private void handleNewCard() {
        JTextField questionField = new JTextField();
        JTextField answerField = new JTextField();

        Object[] message = {
                "Question:", questionField,
                "Answer:", answerField
        };

        int option = JOptionPane.showConfirmDialog(this,
                message,
                "Add Card",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String question = questionField.getText();
            String answer = answerField.getText();
            Card newCard = new Card(question, answer);
            deck.addCardToDeck(newCard);

            refreshCardTable();
        }
    }

    // MODIFIES: this
    // EFFECTS: Accepts input from user and finds card in deck based on index number.
    //          Deletes card and refreshes table model. If no card selected,
    //          display error message.
    private void handleDeleteCard() {
        int[] selectedRows = cardTable.getSelectedRows();

        if (selectedRows.length > 0) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete the selected card(s)?",
                    "Delete Card",
                    JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int rowIndex = selectedRows[i];
                    int cardIndex = (int) tableModel.getValueAt(rowIndex, 0);

                    deck.removeCardFromDeck(cardIndex - 1);

                    refreshCardTable();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select one or more cards to delete.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Goes through all cards in deck and resets CorrectlyAnswer.
    //          Refresh table after.
    private void handleResetAttempts() {
        for (Card card : deck.getAllCards()) {
            card.resetCorrectlyAnswered();
        }
        refreshCardTable();
        JOptionPane.showMessageDialog(this, "All cards reset!");
    }

    // MODIFIES: this
    // EFFECTS: Hides this window and starts PracticeUI,
    //          passing along relevant deck.
    private void handlePracticeAll() {
        // Logic for Practice All button
        setVisible(false);
        new PracticeUI(deck.getAllCards(), this);
    }

    // MODIFIES: this
    // EFFECTS: Hides this window and starts PracticeUI,
    //          passing along relevant deck.
    private void handlePracticeNew() {
        if (!(deck.getUnansweredCards().size() == 0)) {
            setVisible(false);
            new PracticeUI(deck.getUnansweredCards(), this);
        } else {
            JOptionPane.showMessageDialog(this, "No new cards to study!");
        }
    }

    // EFFECTS: saves Card and Deck objects into a JSON file
    private void handleSave() {
        // Logic for Save button
        JsonWriter jsonWriter = new JsonWriter(filePath);
        try {
            jsonWriter.open();
            System.out.println("Successfully ");
            jsonWriter.write(this.deck);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved " + deck.getName() + " to " + filePath);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + filePath);
        }
    }

    // EFFECTS: Closes application when button clicked.
    private void handleQuit() {
        // Logic for Quit button
        printLog();
        System.exit(0);
    }
}
