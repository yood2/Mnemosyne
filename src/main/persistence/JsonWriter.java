package persistence;

import model.Deck;
import org.json.JSONObject;

import java.io.*;

// Code influenced by the JsonSerizalizationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String filePath;

    // JsonWriter object that writes JSON representation of flashcards and deck objects to file
    public JsonWriter(String filePath) {
        this.filePath = filePath;
    }

    // MODIFIES: this
    // EFFECTS: opens writer object at filePath. Throws FileNotFound exception
    // if destination file cannot be opened.
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(filePath));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of the Deck object to file
    public void write(Deck deck) {
        JSONObject json = deck.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
