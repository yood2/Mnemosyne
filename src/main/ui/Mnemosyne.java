package ui;

import javax.swing.*;
import model.Event;
import model.EventLog;

public class Mnemosyne extends JFrame {

    public static final int WIDTH = 420;
    public static final int HEIGHT = 420;
    public static final String filePath = "./data/deckSave.json";

    // Constructs main window
    // EFFECTS: sets up window in which Title will be displayed.
    public Mnemosyne() {
        SwingUtilities.invokeLater(() -> {
            new TitleUI(filePath);
        });
    }

    // EFFECT: prints Events from EventLog
    public static void printLog() {
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }

    // Calls UI Constructor
    public static void main(String[] args) {
        new Mnemosyne();
    }
}