package ui;

import model.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static ui.Mnemosyne.printLog;

public class PracticeUI extends JFrame {

    private List<Card> cards;
    private int currentCardIndex;
    private JLabel questionLabel;
    private JTextField answerTextField;
    private JButton nextButton;
    private WorkspaceUI workspace;
    private String fileString = "./assets/happycat.gif";

    // EFFECTS: Constructs PracticeUI class and initializes fields and swing components
    public PracticeUI(List<Card> cards, WorkspaceUI workspace) {
        super("Practice");
        this.cards = cards;
        this.currentCardIndex = 0;
        this.workspace = workspace;
        this.questionLabel = new JLabel();
        this.answerTextField = new JTextField(20);
        this.nextButton = new JButton("Next");
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: Initializes swing components and default window settings
    private void initializeGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(Mnemosyne.WIDTH, Mnemosyne.HEIGHT));

        addShutdownFunction();

        nextButton.addActionListener(e -> checkAnswer());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(questionLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(answerTextField, gbc);

        gbc.gridx = 1;
        panel.add(nextButton, gbc);

        loadCard();

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    // EFFECTS: Loads next card and sets labels to display cards question and answer
    private void loadCard() {
        if (currentCardIndex < cards.size()) {
            Card currentCard = cards.get(currentCardIndex);
            questionLabel.setText(currentCard.getQuestion());
            answerTextField.setText("");
            answerTextField.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, "Quiz completed!");
            workspace.setVisible(true);
            workspace.refreshCardTable();
            dispose();
        }
    }

    // EFFECTS: Compares user input to card objects correct answer. If correct,
    //          increment field in card object. If incorrect, display correct
    //          answer.
    private void checkAnswer() {
        String userAnswer = answerTextField.getText().trim();
        Card currentCard = cards.get(currentCardIndex);
        JPanel imgPanel = new JPanel();

        if (userAnswer.equalsIgnoreCase(currentCard.getAnswer())) {
            imgPanel.add(new JLabel(new ImageIcon("./assets/happycat.gif")));
            imgPanel.add(new JLabel("You got it! Yay!"));

            JOptionPane.showMessageDialog(this, imgPanel, "Correct!", JOptionPane.PLAIN_MESSAGE);
            currentCard.answeredCorrectly();
        } else {
            imgPanel.add(new JLabel(new ImageIcon("./assets/huhcat.gif")));
            imgPanel.add(new JLabel("Incorrect. Correct answer is: " + currentCard.getAnswer()));

            JOptionPane.showMessageDialog(this, imgPanel, "Incorrect", JOptionPane.PLAIN_MESSAGE);
        }

        currentCardIndex++;
        loadCard();
    }
}
