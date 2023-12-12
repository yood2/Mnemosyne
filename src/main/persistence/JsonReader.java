package persistence;

import model.Card;
import model.Deck;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Code influenced by the JsonSerizalizationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReader {
    private String filePath;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String filePath) {
        this.filePath = filePath;
    }

    // EFFECTS: reads deck from file and returns it;
    // throws IOException if an error occurs while reading data from file.
    public Deck read() throws IOException {
        String jsonData = readFile(filePath);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDeck(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Deck parseDeck(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Deck newDeck = new Deck(name);
        addCards(newDeck, jsonObject);
        return newDeck;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addCards(Deck newDeck, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(newDeck, nextCard);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addCard(Deck newDeck, JSONObject jsonObject) {
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        int correctlyAnswered = jsonObject.getInt("correctlyAnswered");
        Card card = new Card(question, answer);

        if (correctlyAnswered > 0) {
            for (int i = 0; i < correctlyAnswered; i++) {
                card.answeredCorrectly();
            }
        }

        newDeck.addCardToDeck(card);
    }
}
